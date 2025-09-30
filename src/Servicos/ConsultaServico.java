package Servicos;

import entidades.consulta.Consulta;
import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;
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


}
