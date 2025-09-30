package Servicos;

import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;
import entidades.plano.PlanoSaude;
import repositorio.PacienteRepositorio;
import java.util.ArrayList;
import java.util.List;

public class PacienteServico {
    private List<Paciente> pacientes;
    private PacienteRepositorio repositorio;

    public PacienteServico(){
        this.repositorio = new PacienteRepositorio();
        this.pacientes = repositorio.carregarPacientes();

        if (this.pacientes ==  null){
            this.pacientes = new ArrayList<>();
        }
    }
    //busca paciente na memoria
    public Paciente buscarPacienteCpf(String cpf){
        for (Paciente p : pacientes){
            if (p.getCpf().equals(cpf)){
                return p;
            }
        }
        return null;
    }
//cadastro do paciente
    public void cadastrarPacienteComum(String nome, String cpf, int idade){
        if (buscarPacienteCpf(cpf) != null){
            System.err.println(("Erro: CPF já cadastrado!"));
            return;
        }
        Paciente novoPaciente = new Paciente(nome, cpf, idade);
        this.pacientes.add(novoPaciente);
        repositorio.salvarPacientes(this.pacientes);
        System.out.println("Paciente Comum cadastrado com sucesso!!");
    }
    //cadastro do paciente especial

    public void cadastrarPacienteEspecial(String nome, String cpf, int idade, PlanoSaude plano){
        if (buscarPacienteCpf(cpf) != null){
            System.err.println("Erro: CPF já cadstrado");
            return;
        }
        PacienteEspecial novoPaciente = new PacienteEspecial(nome,cpf,idade,plano);
        this.pacientes.add(novoPaciente);
        repositorio.salvarPacientes(this.pacientes);
        System.out.println("Paciente Especial cadastrado com sucesso!");
    }

    //lista completa dos pacientes

    public List<Paciente> listarPacientes(){
        return this.pacientes;
    }

}
