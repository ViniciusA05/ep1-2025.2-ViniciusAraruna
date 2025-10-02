package repositorio;

import entidades.plano.PlanoSaude;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanoSaudeRepositorio {
    private static final String NOME_ARQUIVO = "planos_saude.txt";

    //salvar os planos de saude
    public void salvarPlanoSaude(List<PlanoSaude> planos ){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))){
            for(PlanoSaude plano : planos){
                String linha = String.format("NomePlano: %s;DescontoIdoso: %.2f;Plano especial: %b",
                        plano.getNomePlano(),
                        plano.getDescontoIdoso(),
                        plano.isPlanoEspecial()
                        );
                if (!plano.getDescontosEspecialidade().isEmpty()){
                    linha += ";Descontos:";
                    for (Map.Entry<String,Double> entry: plano.getDescontosEspecialidade().entrySet()){
                        linha+= entry.getKey() + "-" + entry.getValue() + ",";
                    }
                    linha = linha.substring(0,linha.length() -1);
                }
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Planos de Saude salvos com sucesso!!");
        }
        catch (IOException e){
            System.err.println("Erro ao salvar planos de saude: " + e.getMessage());
        }
    }

    //carregar os planos

    public List<PlanoSaude> carregarPlanoSaude(){
        List<PlanoSaude> planos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))){
            String linha;
            while ((linha = reader.readLine()) !=null){
                String[] dados = linha.split(";");
                String nomePlano = dados[0].split(":")[1];
                double descontoIdoso = Double.parseDouble(dados[1].split(":")[1]);
                boolean isPlanoEspecial = Boolean.parseBoolean(dados[2].split(":")[1]);

                PlanoSaude plano = new PlanoSaude(nomePlano, descontoIdoso, isPlanoEspecial);

                if(dados.length>3 && dados[3].startsWith("Descontos: ")){
                    String descontosStr = dados[3].split(":")[1];
                    String[] pares = descontosStr.split(",");
                    for (String par: pares){
                        String[] partes = par.split("-");
                        plano.adicionarDescontoEspecialidade(partes[0], Double.parseDouble(partes[1]));
                    }
                }
                planos.add(plano);
            }
            System.out.println("Planos de Saude carregados com sucesso!!");
        }
        catch (FileNotFoundException e){
            System.err.println("Arquivo de Plano de Saude nao encontrado. Comecando com Lista vazia.");
        }
        catch (IOException e){
            System.err.println("Erro ao carregar planos de saude: " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Erro de formatacao no arquivo de planos. Verifique o formato.");
        }
        return planos;
    }
}
