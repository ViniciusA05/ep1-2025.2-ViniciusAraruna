package repositorio;

import entidades.plano.PlanoSaude;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class PlanoSaudeRepositorio {

    private static final String NOME_ARQUIVO = "planos_saude.csv";
    //salvar os planos de saude
    public void salvarPlanosSaude(List<PlanoSaude> planos) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (PlanoSaude plano : planos) {
            String linha = String.format(Locale.US, "NomePlano:%s,DescontoIdoso:%.2f,IsPlanoEspecial:%b",
                        plano.getNomePlano(), plano.getDescontoIdoso(), plano.isPlanoEspecial()
                );

                if (!plano.getDescontosEspecialidade().isEmpty()) {
                    linha += ",Descontos:";
                    for (Map.Entry<String, Double> entry : plano.getDescontosEspecialidade().entrySet()) {
                        linha += entry.getKey() + "-" + entry.getValue() + ",";
                    }
                    linha = linha.substring(0, linha.length() - 1);
                }

                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Plano de Saúde salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar planos de saúde: " + e.getMessage());
        }
    }
    //carregar os planos
    public List<PlanoSaude> carregarPlanosSaude() {
        List<PlanoSaude> planos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                try {
                    int indexDescontos = linha.indexOf(",Descontos:");
                    String dadosBase;
                    String descontosStr = "";

                    if (indexDescontos != -1) {
                        dadosBase = linha.substring(0, indexDescontos);
                        descontosStr = linha.substring(indexDescontos + 1);
                    } else {
                        dadosBase = linha;
                    }

                    String[] dados = dadosBase.split(",");

                    String nomePlano = dados[0].split(":")[1].trim();
                    double descontoIdoso = Double.parseDouble(dados[1].split(":")[1].trim());
                    boolean isPlanoEspecial = Boolean.parseBoolean(dados[2].split(":")[1].trim());

                    PlanoSaude plano = new PlanoSaude(nomePlano, descontoIdoso, isPlanoEspecial);
                    if (!descontosStr.isEmpty()) {

                        String dadosDescontos = descontosStr.split(":")[1].trim();

                        String[] pares = dadosDescontos.split(",");

                        for (String par : pares) {
                            String parLimpo = par.trim();
                            if (parLimpo.isEmpty()) continue;

                            String[] partes = parLimpo.split("-");

                            if (partes.length == 2) {
                                plano.adicionarDescontoEspecialidade(
                                        partes[0].trim(),
                                        Double.parseDouble(partes[1].trim())
                                );
                            }
                        }
                    }
                    planos.add(plano);

                } catch (Exception e) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo de planos. Erro: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de planos de saúde não encontrado. Começando com lista vazia.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar planos de saúde: " + e.getMessage());
        }
        return planos;
    }
}
