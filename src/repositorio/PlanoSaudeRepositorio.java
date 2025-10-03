package repositorio;

import entidades.plano.PlanoSaude;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanoSaudeRepositorio {

    private static final String NOME_ARQUIVO = "planos_saude.csv";
    //salvar os planos de saude
    public void salvarPlanosSaude(List<PlanoSaude> planos) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (PlanoSaude plano : planos) {
                String linha = String.format("NomePlano:%s,DescontoIdoso:%.2f,IsPlanoEspecial:%b",
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
                    String[] dados = linha.split(",");

                    String nomePlano = dados[0].split(":")[1].trim();
                    double descontoIdoso = Double.parseDouble(dados[1].split(":")[1].trim());
                    boolean isPlanoEspecial = Boolean.parseBoolean(dados[2].split(":")[1].trim());

                    PlanoSaude plano = new PlanoSaude(nomePlano, descontoIdoso, isPlanoEspecial);

                    if (dados.length > 3 && dados[3].startsWith("Descontos:")) {
                        String descontosStr = dados[3].split(":")[1];
                        String[] pares = descontosStr.split(",");
                        for (String par : pares) {
                            String[] partes = par.split("-");
                            plano.adicionarDescontoEspecialidade(partes[0].trim(), Double.parseDouble(partes[1].trim()));
                        }
                    }
                    planos.add(plano);

                } catch (Exception e) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo de planos.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar planos de saúde: " + e.getMessage());
        }
        return planos;
    }
}
