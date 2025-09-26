package repositorio;

import entidades.internacao.Internacao;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InternacaoRepositorio {
    private static final String NOME_ARQUIVO = "internacoes.txt";

    //salvar internacoes

    public void salvarInternacao(List<Internacao> internacoes){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))){
            for (Internacao internacao : internacoes){
                String dataSaidaStr = internacao.getDataSaida() != null ? internacao.getDataSaida().toString() : "null";

                String linha = String.format("Quarto: %d;Entrada: %s;Saida: %s;Custo: %.2f;Ativa: %b;PacienteCPF: %s; MedicoCRM: %s",
                        internacao.getNumeroQuarto(),
                        internacao.getDataEntrada().toString(),
                        dataSaidaStr,
                        internacao.getCustoInternacao(),
                        internacao.isInternacaoAtiva(),
                        internacao.getPaciente().getCpf(),
                        internacao.getMedicoResponsavel().getCRM()
                        );
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Interacoes salvas com sucesso!!");
        }
        catch (IOException e){
            System.err.println("Erro ao salvar internações: " + e.getMessage());
        }
    }

    //carregar as internacoes
    public List<Internacao> carregarInterncoes(){
        List<Internacao> internacoes = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] dados = linha.split(";");
                int numeroQuarto = Integer.parseInt(dados[0].split(":")[1]);
                LocalDateTime dataEntrada = LocalDateTime.parse(dados[1].split(":")[1]);

                String dataSaidaStr = dados[2].split(":")[1];
                LocalDateTime dataSaida = dataSaidaStr.equals("null") ? null : LocalDateTime.parse(dataSaidaStr);

                double custoInternacao = Double.parseDouble(dados[3].split(":")[1]);
                boolean internacaoAtiva = Boolean.parseBoolean(dados[4].split(":")[1]);
                String pacienteCpf = dados[5].split(":")[1];
                String medicoCrm = dados[6].split(":")[1];

                Internacao internacao = new Internacao(null,null,numeroQuarto,dataEntrada);
                internacao.darAlta(dataSaida, custoInternacao);

                internacoes.add(internacao);
            }
            System.out.println("Internações carregadas com sucesso!!");
        }
        catch (FileNotFoundException e){
            System.err.println("Arquivo de internações não encontrado. Começando com lista vazia.");
        }
        catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println("Erro ao carregar internações. Verifique o formato do arquivo.");
        }
        return internacoes;
    }

}
