package entidades.plano;

import java.util.HashMap;
import java.util.Map;

public class PlanoSaude {
    private String nomePlano;
    private Map<String, Double> descontosEspecialidade;
    private Double descontoIdoso;
    private boolean ePlanoEspecial;

    public PlanoSaude (String nomePlano, double descontoIdoso, boolean isPlanoEspecial){
        this.nomePlano = nomePlano;
        this.ePlanoEspecial = isPlanoEspecial;
        this.descontoIdoso = descontoIdoso;
        this.descontosEspecialidade = new HashMap<>();
    }

    public String getNomePlano(){
        return nomePlano;
    }

    public Map<String, Double> getDescontosEspecialidade() {
        return descontosEspecialidade;
    }

    public Double getDescontoIdoso() {
        return descontoIdoso;
    }

    public boolean isPlanoEspecial() {
        return isPlanoEspecial();
    }
    public void adicionarDescontoEspecialidade(String especialidade, double percentual){
        this.descontosEspecialidade.put(especialidade, percentual);
    }
    public double getDescontoParaEspecialidade(String especialidade){
        return this.descontosEspecialidade.getOrDefault(especialidade, 0.0);
    }

}
