package entidades.pessoa;

import entidades.plano.PlanoSaude;

public class PacienteEspecial extends Paciente {
    private PlanoSaude planoSaude;

    public PacienteEspecial(String nome, String cpf, int idade, PlanoSaude planoSaude){
        super(nome, cpf, idade);
        this.planoSaude = planoSaude;
    }
    public PlanoSaude getPlanoSaude(){

        return planoSaude;
    }
}
