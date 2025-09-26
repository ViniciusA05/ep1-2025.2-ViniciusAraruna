package repositorio;

import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;
import entidades.plano.PlanoSaude;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio {
    private static final String NOME_ARQUIVO = "paciente.txt";

    //salvar a lista de pacientes
    public void salvarPacientes(List<Paciente> pacientes){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))){
            for (Paciente paciente : pacientes){
                String linha = String.format("Nome: %s; Cpf: %s; Idade: %d",
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getIdade());
                if(paciente instanceof PacienteEspecial){
                    PacienteEspecial pe = (PacienteEspecial) paciente;
                    linha += String.format("Tipo: Especial;Plano de Saúde: %s",
                    pe.getPlanoSaude().getNomePlano()
                            );
                }
                else {
                    linha+="Tipo: Comum;";
                }
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Pacientes salvos com sucesso!!");
        }
        catch (IOException e){
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }
    //carregar a lista de pacientes
    public List<Paciente> carregarPacientes(){
        List<Paciente> pacientes = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))){
            String linha;
            while ((linha = reader.readLine()) !=null){
                String[] dados = linha.split(";");

                String nome = dados[0].split(":")[1];
                String cpf = dados[1].split(":")[1];
                int idade = Integer.parseInt(dados[2].split(":")[1]);
                String tipoPaciente = dados[3].split(":")[1];

                Paciente paciente;
                if (tipoPaciente.equals("Especial")){
                    String nomePlano = dados[4].split(":")[1];
                    PlanoSaude plano = new PlanoSaude(nomePlano, 0.0, true);

                    paciente = new PacienteEspecial(nome,cpf,idade,plano);
                }
                else {
                    paciente = new Paciente(nome,cpf,idade);
                }
                pacientes.add(paciente);
            }
            System.out.println("Pacientes carregados com sucesso!!");
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo de pacientes não encontrado. Começando com a lista vazia. ");
        }
        catch (IOException e){
            System.err.println("Erro ao carregar pacientes" + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Erro no formato do arquivo pacientes");
        }
        return pacientes;
    }
}
