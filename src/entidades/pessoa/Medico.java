package entidades.pessoa;

import entidades.consulta.Consulta;

import java.util.ArrayList;
import java.util.List

public class Medico extends Pessoa {

    private String CRM;
    private String especialidade;
    private double custoConsulta;
    private List<Consulta> agenda;

    public Medico(String nome, String cpf, int idade, String CRM,String especialidade, double custoConsulta){
        super(nome, cpf, idade);

        this.CRM = CRM;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;
        this.agenda = new ArrayList<>()
    }
    public String getCRM(){
        return CRM;
    }
    public void setCRM(String CRM){
        this.CRM = CRM;
    }
    public String getEspecialidade(){
        return especialidade;
    }
    public void setEspecialidade(String especialidade){
        this.especialidade = especialidade;
    }

    public double getCustoConsulta() {
        return custoConsulta;
    }
    public void setCustoConsulta(double custoConsulta){
        this.custoConsulta = custoConsulta;
    }

    public List<Consulta> getAgenda() {
        return agenda;
    }

    public void adicionarConsulta(Consulta consulta){
        this.agenda.add(consulta);
    }
}
