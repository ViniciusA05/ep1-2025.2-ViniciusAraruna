package entidades.internacao;

import entidades.pessoa.Medico;
import entidades.pessoa.Paciente;

import java.time.LocalDateTime;

public class Internacao {
    private Medico medicoResponsavel;
    private Paciente paciente;

    private int numeroQuarto;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private double custoInternacao;
    private boolean internacaoAtiva;

    public Internacao(Paciente paciente, Medico medicoResponsavel, int numeroQuarto, LocalDateTime dataEntrada){
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.numeroQuarto = numeroQuarto;
        this.internacaoAtiva = true;
        this.dataEntrada = dataEntrada;
    }
    public Paciente getPaciente(){
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }
    public double getCustoInternacao(){
        return custoInternacao;
    }

    public boolean isInternacaoAtiva() {
        return internacaoAtiva;
    }

    //para dar alta**
    public void darAlta(LocalDateTime dataSaida, double custoInternacao){
        this.dataSaida = dataSaida;
        this.custoInternacao = custoInternacao;
        this.internacaoAtiva = false;
    }

}
