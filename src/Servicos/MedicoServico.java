package Servicos;

import entidades.pessoa.Medico;
import repositorio.MedicoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class MedicoServico {
    private List<Medico> medicos;
    private MedicoRepositorio repositorio;

    public MedicoServico(){
        this.repositorio = new MedicoRepositorio();
        this.medicos = repositorio.carregarMedicos();

        //garantia de q a lista nao esteja nula
        if (this.medicos == null){
            this.medicos = new ArrayList<>();
        }
    }
    public Medico buscarMedicoPorCrm(String crm){
        for (Medico m : medicos){
            if (m.getCRM().equals(crm)){
                return m;
            }
        }
        return null;
    }

    public void cadastrarMedicos(String nome, String cpf, int idade, String crm, String especialidade, double custoConsulta){
        if(buscarMedicoPorCrm(crm) != null){
            System.err.println("Erro: CRM ja cadastrado!");
            return;
        }
        Medico novoMedico = new Medico(nome,cpf,idade,crm,especialidade,custoConsulta);
        this.medicos.add(novoMedico);
        //salvar lista atualizada
        repositorio.salvarMedicos(this.medicos);
        System.out.println("Médico: " + nome +" cadastrado com sucesso!!");
    }

    public List<Medico> ListarMedicos() {
        return this.medicos;
    }
}
