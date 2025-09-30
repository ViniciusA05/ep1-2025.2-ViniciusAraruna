package Servicos;

import entidades.internacao.Internacao;
import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;
import repositorio.InternacaoRepositorio;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InternacaoServico {
    private List<Internacao> internacoes;
    private InternacaoRepositorio repositorio;
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;

    public InternacaoServico(PacienteServico pacienteServico, MedicoServico medicoServico){
        this.repositorio = new InternacaoRepositorio();
        this.internacoes = repositorio.carregarInternacoes();
        if(this.internacoes == null){
            this.internacoes = new ArrayList<>();
        }
        this.pacienteServico = pacienteServico;
        this.medicoServico = medicoServico;
    }
    //internacao do paciente

    public boolean internarPaciente(String pacienteCpf, String medicoCrm, int numeroQuarto, LocalDateTime dataEntrada){
        Paciente paciente = pacienteServico.buscarPacienteCpf(pacienteCpf);
        Medico medico = medicoServico.buscarMedicoPorCrm(medicoCrm);
        if(paciente == null || medico == null){
            System.err.println("Erro: Paciente ou Médico não encontrado no sistema ");
            return false;
        }
        Internacao novaInternacao = new Internacao(paciente,medico,numeroQuarto,dataEntrada);
        this.internacoes.add(novaInternacao);
        paciente.adicionarInternacaoHistorico(novaInternacao);
        repositorio.salvarInternacao(this.internacoes);
        System.out.println("Paciente " + paciente.getNome() + "internado no quarto " + numeroQuarto);
        return true;
    }
    //alta e custo da internacao


    public boolean darAlta(Internacao internacao, double custoBase){
        LocalDateTime dataSaida = LocalDateTime.now();
        long diasInternado = Duration.between(internacao.getDataEntrada(), dataSaida).toDays();
        double custoFinal = custoBase;

        if (internacao.getPaciente() instanceof PacienteEspecial){
            PacienteEspecial pe = (PacienteEspecial) internacao.getPaciente();
            if (pe.getPlanoSaude().isPlanoEspecial() && diasInternado < 7 ){
                System.out.println("Desconto aplicado! Internação gratuita pelo plano de saude!");
                custoFinal = 0.0;
            }
        }
        internacao.darAlta(dataSaida, custoFinal);
        repositorio.salvarInternacao(this.internacoes);
        return true;
    }
    //verificar internacao ativa no quarto

    private boolean verificarQuartoOcupado(int numeroQuarto){
        for (Internacao i : internacoes){
            if (i.isInternacaoAtiva() && i.getNumeroQuarto() == numeroQuarto){
                return true;
            }
        }
        return false;
    }
}
