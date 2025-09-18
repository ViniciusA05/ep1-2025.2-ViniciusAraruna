package entidades.pessoa;

import entidades.consulta.Consulta;
import entidades.internacao.Internacao;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa{

    private List<Consulta> historicoConsultas;
    private List<Internacao> historicoInternacoes;

    public Paciente(String nome, String cpf, int idade){
        super(nome, cpf, idade);

        this.historicoConsultas = new ArrayList<>();
        this.historicoInternacoes = new ArrayList<>();
    }

    public List<Consulta> getHistoricoConsultas() {
        return historicoConsultas;
    }

    public List<Internacao> getHistoricoInternacoes() {
        return historicoInternacoes;
    }
    public void adicionarConsultaHistorico(Consulta consulta){
        this.historicoConsultas.add(consulta);
    }
    public void adicionarInternacaoHistorico(Internacao internacao){
        this.historicoInternacoes.add(internacao);
    }
}
