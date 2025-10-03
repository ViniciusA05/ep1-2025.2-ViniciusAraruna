package repositorio;

import entidades.pessoa.Medico;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositorio {

    private static final String NOME_ARQUIVO = "medicos.csv";

    //construir a lista de medicos:
    public void salvarMedicos(List<Medico> medicos) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new java.io.FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (Medico medico : medicos) {
                String linha = String.format("Nome:%s,Cpf:%s,Idade:%d,CRM:%s,Especialidade:%s,CustoConsulta:%.2f",
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
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    //carregar a lista de medicos:
    public List<Medico> carregarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new java.io.FileInputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                try {
                    String[] dados = linha.split(",");
                    if (dados.length < 6) { throw new IllegalArgumentException("Dados incompletos."); }

                    String nome = dados[0].split(":")[1].trim();
                    String cpf = dados[1].split(":")[1].trim();
                    int idade = Integer.parseInt(dados[2].split(":")[1].trim());
                    String crm = dados[3].split(":")[1].trim();
                    String especialidade = dados[4].split(":")[1].trim();
                    double custoConsulta = Double.parseDouble(dados[5].split(":")[1].trim());

                    Medico medico = new Medico(nome, cpf, idade, crm, especialidade, custoConsulta);
                    medicos.add(medico);
                } catch (Exception lineException) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo CSV de médicos. " + lineException.getMessage());
                }
            }
            System.out.println("Médicos carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de médicos não encontrado. Começando com lista vazia.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
        }
        return medicos;
    }
}
