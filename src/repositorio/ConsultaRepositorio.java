package repositorio;

import entidades.consulta.Consulta;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepositorio {

    private static final String NOME_ARQUIVO = "consultas.csv";
    //salvar consultas
    public void salvarConsultas(List<Consulta> consultas) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (Consulta consulta : consultas) {
                String linha = String.format("DataHora:%s,Local:%s,Custo:%.2f,PacienteCPF:%s,MedicoCRM:%s",
                        consulta.getDataHora().toString(),
                        consulta.getLocal(),
                        consulta.getCustoConsulta(),
                        consulta.getPaciente().getCpf(),
                        consulta.getMedico().getCRM()
                );
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Consultas salvas em formato CSV!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }
    //carregar consultas
    public List<Consulta> carregarConsultas() {
        List<Consulta> consultas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                try {
                    String[] dados = linha.split(",");

                    if (dados.length < 5) { throw new IllegalArgumentException("Dados incompletos."); }
                    LocalDateTime dataHora = LocalDateTime.parse(dados[0].split(":")[1].trim());
                    String local = dados[1].split(":")[1].trim();
                    double custo = Double.parseDouble(dados[2].split(":")[1].trim());

                    Consulta consulta = new Consulta(null, null, dataHora, local);
                    consulta.setCustoConsulta(custo);

                    consultas.add(consulta);
                } catch (Exception e) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo de consultas. " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar consultas: " + e.getMessage());
        }
        return consultas;
    }
}
