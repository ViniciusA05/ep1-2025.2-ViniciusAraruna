package Servicos;

import entidades.internacao.Internacao;
import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;
import repositorio.InternacaoRepositorio;

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

}
