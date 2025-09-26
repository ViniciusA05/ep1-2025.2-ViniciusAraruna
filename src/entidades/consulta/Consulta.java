package entidades.consulta;

import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;

import java.time.LocalDateTime;

public class Consulta {
    private Paciente paciente;
    private Medico medico;

    private LocalDateTime dataHora;
    private String local;
    private String status;
    private String diagnostico;
    private String prescricaoMedicamentos;
    private double custoConsulta;

    public Consulta (Paciente paciente, Medico medico,LocalDateTime dataHora, String local){
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = "Agendada";//Status: concluida, em processo, disponível...
        this.custoConsulta = 0.0;
    }

    public Paciente getPaciente(){
        return paciente;
    }
    public Medico getMedico(){
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public String getLocal(){
        return local;
    }
    public String getStatus(){
        return status;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getPrescricaoMedicamentos() {
        return prescricaoMedicamentos;
    }

    public double getCustoConsulta() {
        return custoConsulta;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setPrescricaoMedicamentos(String prescricaoMedicamentos) {
        this.prescricaoMedicamentos = prescricaoMedicamentos;
    }

    public void setCustoConsulta(double custoConsulta) {
        this.custoConsulta = custoConsulta;
    }
}
