package Servicos;

import entidades.plano.PlanoSaude;
import repositorio.PlanoSaudeRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanoSaudeServico {
    private List<PlanoSaude> planos;
    private PlanoSaudeRepositorio repositorio;

    public PlanoSaudeServico(){
        this.repositorio = new PlanoSaudeRepositorio();
        this.planos = repositorio.carregarPlanosSaude();
        if (this.planos == null){
            this.planos = new ArrayList<>();
        }
    }
    //cadastrar um novo plano

    public void cadastrarPlano(String nomePlano,double descontoIdoso,boolean isPlanoEspecial, Map<String,Double> descontosEspeciais){
        if (buscarPlanoPorNome(nomePlano) != null){
            System.err.println("Erro: Plano de Saúde com este nome já esta cadastrado");
            return;
        }
        PlanoSaude novoPlano = new PlanoSaude(nomePlano,descontoIdoso,isPlanoEspecial);
        for (Map.Entry<String, Double> entry : descontosEspeciais.entrySet()){
            novoPlano.adicionarDescontoEspecialidade(entry.getKey(), entry.getValue());
        }
        this.planos.add(novoPlano);
        repositorio.salvarPlanosSaude(this.planos);
        System.out.println("Plano de Saúde '" + nomePlano +"' cadastrado com sucesso!!");
    }
    //metodo de buscar plano
    public PlanoSaude buscarPlanoPorNome(String nome){
        for (PlanoSaude p : planos){
            if (p.getNomePlano().equalsIgnoreCase(nome)){
                return p;
            }
        }
        return null;
    }
    //retornar a lista completa de planos
    public List<PlanoSaude> listarPlanos(){
        return this.planos;
    }
}
