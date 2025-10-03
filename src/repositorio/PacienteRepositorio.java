package repositorio;

import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;
import entidades.plano.PlanoSaude;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio {

    private static final String NOME_ARQUIVO = "pacientes.csv";
    //salvar a lista de pacientes
    public void salvarPacientes(List<Paciente> pacientes) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            for (Paciente paciente : pacientes) {
                String linha = String.format("Nome:%s,Cpf:%s,Idade:%d,",
                        paciente.getNome(), paciente.getCpf(), paciente.getIdade()
                );

                if (paciente instanceof PacienteEspecial) {
                    PacienteEspecial pe = (PacienteEspecial) paciente;
                    linha += String.format("Tipo:Especial,PlanoSaude:%s",
                            pe.getPlanoSaude().getNomePlano()
                    );
                } else {
                    linha += "Tipo:Comum";
                }

                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }
    //carregar a lista de pacientes
    public List<Paciente> carregarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(NOME_ARQUIVO), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                try {
                    String[] dados = linha.split(",");

                    String nome = dados[0].split(":")[1].trim();
                    String cpf = dados[1].split(":")[1].trim();
                    int idade = Integer.parseInt(dados[2].split(":")[1].trim());
                    String tipoPaciente = dados[3].split(":")[1].trim();

                    Paciente paciente;
                    if (tipoPaciente.equals("Especial")) {
                        String nomePlano = dados[4].split(":")[1].trim();
                        PlanoSaude plano = new PlanoSaude(nomePlano, 0.0, true);
                        paciente = new PacienteEspecial(nome, cpf, idade, plano);
                    } else {
                        paciente = new Paciente(nome, cpf, idade);
                    }
                    pacientes.add(paciente);

                } catch (Exception e) {
                    System.err.println("AVISO: Linha de dados corrompida ignorada no arquivo de pacientes.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}
