package repositorio;

import entidades.internacao.Internacao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InternacaoRepositorio {

    private static final String NOME_ARQUIVO = "internacoes.csv";
    //salvar consultas
    public void salvarInternacao(List<Internacao> internacoes) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (Internacao internacao : internacoes) {
                String dataSaidaStr = internacao.getDataSaida() != null ? internacao.getDataSaida().toString() : "null";
                String linha = String.format("Quarto:%d,Entrada:%s,Saida:%s,Custo:%.2f,Ativa:%b,PacienteCPF:%s,MedicoCRM:%s",
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
            System.out.println("Internações salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar internações: " + e.getMessage());
        }
    }
    //carregar consultas
    public List<Internacao> carregarInternacoes() {
        List<Internacao> internacoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                try {
                    String[] dados = linha.split(",");

                    int numeroQuarto = Integer.parseInt(dados[0].split(":")[1].trim());
                    LocalDateTime dataEntrada = LocalDateTime.parse(dados[1].split(":")[1].trim());
                    String dataSaidaStr = dados[2].split(":")[1].trim();
                    LocalDateTime dataSaida = dataSaidaStr.equals("null") ? null : LocalDateTime.parse(dataSaidaStr);

                    double custoInternacao = Double.parseDouble(dados[3].split(":")[1].trim());
                    Internacao internacao = new Internacao(null, null, numeroQuarto, dataEntrada);
                    internacao.darAlta(dataSaida, custoInternacao);

                    internacoes.add(internacao);
                } catch (Exception e) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo de internações. " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar internações: " + e.getMessage());
        }
        return internacoes;
    }
}