package repositorio;

import entidades.consulta.Consulta;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepositorio {
    private static final String NOME_ARQUIVO = "consultas.txt";

    //salvar consultas

    public void salvarConsultas(List<Consulta> consultas){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))){
            for(Consulta consulta : consultas){
                String linha = String.format("DataHora:%s;Local:%s; Custo:%.2f;PacienteCPF:%s;MedicoCRM:%s",
                        consulta.getDataHora().toString(),
                        consulta.getLocal(),
                        consulta.getCustoConsulta(),
                        consulta.getPaciente().getCpf(),
                        consulta.getMedico().getCRM()
                );
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Consulta salvas com sucesso!!");
        }
        catch (IOException e){
            System.err.println("Erro ao salvar consultas." + e.getMessage());
        }
    }

    //carregar consultas

    public List<Consulta> carregarConsultas(){
        List<Consulta> consultas = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] dados = linha.split(";");
                LocalDateTime dataHora = LocalDateTime.parse(dados[0].split(":")[1]);
                String local = dados[1].split(":")[1];
                double custo = Double.parseDouble(dados[2].split(":")[1]);
                String pacienteCpf = dados[3].split(":")[1];
                String medicoCrm = dados[4].split(":")[1];

                Consulta consulta = new Consulta(null,null,dataHora,local);
                consultas.add(consulta);
            }
            System.out.println("Consultas carregadas com sucesso!!");
        }
        catch (FileNotFoundException e){
            System.err.println("Arquivo não encontrado. Começando com lista vazia. " + e.getMessage());
        }
        catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.err.println("Erro ao carregar consultas. Verifique o formato do arquivo.");
        }
        return consultas;
    }
}
