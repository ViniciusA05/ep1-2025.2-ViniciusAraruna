package repositorio;

import entidades.pessoa.Medico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositorio {

    private static final String NOME_ARQUIVO = "medicos.txt";
//construir a lista de medicos:

    public void salvarMedicos(List<Medico> medicos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Medico medico : medicos) {
                String linha = String.format("Nome: %s;CPF: %s;Idade: %d;CRM: %s;Especialidade: %s;Custo da Consulta: %.2f",
                        medico.getNome(),
                        medico.getCpf(),
                        medico.getIdade(),
                        medico.getCRM(),
                        medico.getEspecialidade(),
                        medico.getCustoConsulta()
                );
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Médicos salvos com sucesso!!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar Médicos." + e.getMessage());
        }
    }
    //carregar a lista de medicos:

    public List<Medico> carregarMedicos(){
        List<Medico> medicos = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] dados = linha.split(";");
                String nome = dados[0].split(":")[1];
                String cpf = dados[1].split(":")[1];
                int idade = Integer.parseInt(dados[2].split(":")[1]);
                String crm = dados[3].split(":")[1];
                String especialidade = dados[4].split(":")[1];
                double custoConsulta = Double.parseDouble(dados[5].split(":")[1]);

            }
            System.out.println("Médicos carregados com sucesso!!");
        }
        catch (FileNotFoundException e){
            System.err.println("Arquivos de médicos nao encontrado. Começando com lista vazia. " + e.getMessage());
        }
        catch (IOException e){
            System.err.println("Erro ao carregar médicos" + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Erro no formato do arquivo. Verifique a formatação");
        }
        return medicos;
    }
}
