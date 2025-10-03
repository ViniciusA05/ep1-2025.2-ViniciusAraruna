package Servicos;

import entidades.consulta.Consulta;
import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;
import repositorio.ConsultaRepositorio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaServico {
    private List<Consulta> consultas;
    private ConsultaRepositorio repositorio;
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;

    public ConsultaServico(MedicoServico medicoServico, PacienteServico pacienteServico){
        this.repositorio = new ConsultaRepositorio();
        this.consultas = repositorio.carregarConsultas();
        if (this.consultas == null){
            this.consultas = new ArrayList<>();
        }
        this.medicoServico = medicoServico;
        this.pacienteServico = pacienteServico;
    }

    //construindo o agendamento
    public boolean agendarConsulta(String pacienteCpf, String medicoCrm, LocalDateTime dataHora, String local) {
        Paciente paciente = pacienteServico.buscarPacienteCpf(pacienteCpf);
        Medico medico = medicoServico.buscarMedicoPorCrm(medicoCrm);
        if (paciente == null || medico == null) {
            System.err.println("Erro: Paciente ou Médico não encontrado no sistema");
            return false;
        }
        if (verificarConflito(medico, dataHora)) {
            System.err.println("Erro: Médico já tem uma consulta agendada para este horário.");
            return false;
        }
        //nova consulta
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, local);
        double custoFinal = calcularCustoComDesconto(medico,paciente);
        novaConsulta.setCustoConsulta(custoFinal);
        this.consultas.add(novaConsulta);

        paciente.adicionarConsultaHistorico(novaConsulta);
        medico.adicionarConsulta(novaConsulta);

        repositorio.salvarConsultas(this.consultas);

        System.out.println("Consulta agendada com sucesso, com o Dr. " + medico.getNome());
        return true;
    }
    //verificarConflito
    public boolean verificarConflito(Medico medico, LocalDateTime dataHora){
        for (Consulta consultaExistente : medico.getAgenda()){
            if(consultaExistente.getDataHora().isEqual(dataHora)){
                return true;
            }
        }
        return false;
    }
//encontrar uma consulta por medico e data
    private Consulta buscarConsultaPorMedicoeData(String medicoCrm , LocalDateTime dataHora){
        for (Consulta c: consultas){
            if (c.getMedico().getCRM().equals(medicoCrm) && c.getDataHora().isEqual(dataHora) && c.getStatus().equalsIgnoreCase("Agendada"));{
                return c;
            }
        }
        return null;
    }
    //concluir consulta, adicionando prescricao e diagnostico, atualizando o status
    public boolean concluirConsulta(String medicoCrm, LocalDateTime dataHora, String diagnostico, String prescricao){
        Consulta consulta = buscarConsultaPorMedicoeData(medicoCrm,dataHora);
        if (consulta == null){
            System.err.println("Erro: Consulta não encontrada ou já concluida/cancelada.");
            return false;
        }
        consulta.setDiagnostico(diagnostico);
        consulta.setPrescricaoMedicamentos(prescricao);
        consulta.setStatus("Concluída!");

        repositorio.salvarConsultas(this.consultas);
        System.out.println("Consulta concluida e diagnóstico registrado com sucesso!");
        return true;
    }
    public List<Consulta> listaConsultas(){
        return this.consultas;
    }
    //calcular custo com descontos
    private double calcularCustoComDesconto(Medico medico, Paciente paciente) {
        double custoBase = medico.getCustoConsulta();
        double descontoTotal = 0.0;
            if (paciente instanceof PacienteEspecial) {
                PacienteEspecial pe = (PacienteEspecial) paciente;
                if (paciente.eIdoso()) {
                    descontoTotal = pe.getPlanoSaude().getDescontoIdoso();
                } else {
                    double descontoEspec = pe.getPlanoSaude().getDescontoParaEspecialidade(medico.getEspecialidade());
                    descontoTotal = descontoEspec;
                }
            }
        if (descontoTotal > 1.0) descontoTotal = 1.0;
        return custoBase * (1.0 - descontoTotal);
    }

}
