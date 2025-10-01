package Relatorios;

import Servicos.ConsultaServico;
import Servicos.InternacaoServico;
import Servicos.MedicoServico;
import Servicos.PacienteServico;
import entidades.consulta.Consulta;
import entidades.internacao.Internacao;
import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatoriosServico {
    private ConsultaServico consultaServico;
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;
    private InternacaoServico internacaoServico;

    public RelatoriosServico(PacienteServico ps, MedicoServico ms, ConsultaServico cs, InternacaoServico is){
        this.pacienteServico = ps;
        this.consultaServico = cs;
        this.internacaoServico = is;
        this.medicoServico = ms;
    }
    //implmentacao dos relatorios
    public void relatorioPacienteComHistorico(){
        System.out.println("\n--- RELATÓRIO: PACIENTE E HISTÓRICO ---");
        List<Paciente> pacientes = pacienteServico.listarPacientes();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (pacientes.isEmpty()){
            System.out.println("Nenhum paciente cadastrado!");
            return;
        }
        for (Paciente p : pacientes){
            String tipo = (p instanceof PacienteEspecial) ? "Especial" : "Comum";
            System.out.printf("\n Paciente: %s (CPF: %s , Tipo: %s)\n", p.getNome() , p.getCpf(), tipo);
//historico de consultas
            List<Consulta> consultas = p.getHistoricoConsultas();
            System.out.println(" Histórico de Consultas: (" + consultas.size() + "): ");
            if (consultas.isEmpty()){
                System.out.println(" Nenhuma Consulta registrada. ");
            } else{
                for (Consulta c : consultas){
                    String data = c.getDataHora() != null ? c.getDataHora().format(formatter) : "Data indefinida";
                    System.out.printf(" [%s] Dr(a). %s , Status: %s\n",
                            data, c.getMedico().getNome(), c.getStatus());
                }
            }
            //historico de internacoes

            List<Internacao> internacoes = p.getHistoricoInternacoes();
            System.out.println(" Histórico de Internações: (" + internacoes.size() + "): ");
            if (internacoes.isEmpty()){
                System.out.println(" Nenhuma internação registrada. ");
            }else{
                for (Internacao i : internacoes){
                    String entrada = i.getDataEntrada().format(formatter);
                    String saida = i.isInternacaoAtiva() ? "Internado (Ativa) " : i.getDataSaida().format(formatter);
                    System.out.printf(" Quarto: %d \n Entrada: %s \n Saida: %s\n",
                            i.getNumeroQuarto(), entrada,saida);
                }
            }
        }
        System.out.println("");
    }
    //quais pacientes estao internados no momento
    public void relatorioPacientesInternado(){
        System.out.println("\n--- RELATÓRIO: PACIENTES INTERNADOS ATUALMENTE ---");
        List<Internacao> todasInternacoes = internacaoServico.listaInternacao();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        boolean encontrouAtivos = false;

        System.out.println("-----------------------------------------------------");
        for (Internacao i : todasInternacoes){
            if(i.isInternacaoAtiva()){
                encontrouAtivos = true;

                Duration duracao = Duration.between(i.getDataEntrada(), LocalDateTime.now());
                long horas = duracao.toHours();
                long dias = horas/24;
                horas = horas%24;//quanto tempo resta

                System.out.printf(" Quarto: %d\n",i.getNumeroQuarto());
                System.out.printf(" Paciente: %s(CPF: %s)\n",i.getPaciente().getNome(),i.getPaciente().getCpf());
                System.out.printf(" Entrada: %s\n", i.getDataEntrada().format(formatter));
                System.out.printf(" Tempo de internação: %d dias e %d horas\n",dias,horas);
                System.out.println("-----------------------------------------------------");
            }
        }
        if (!encontrouAtivos){
            System.out.println("Nenhum paciente está internado no momento!");
            System.out.println("-----------------------------------------------------");
        }
    }
}
