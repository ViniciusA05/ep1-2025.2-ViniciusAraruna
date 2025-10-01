package Relatorios;

import Servicos.ConsultaServico;
import Servicos.InternacaoServico;
import Servicos.MedicoServico;
import Servicos.PacienteServico;
import entidades.consulta.Consulta;
import entidades.pessoa.Paciente;
import entidades.pessoa.PacienteEspecial;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        if (pacientes.isEmpty()){
            System.out.println("Nenhum paciente cadastrado!");
            return;
        }
        for (Paciente p : pacientes){
            String tipo = (p instanceof PacienteEspecial) ? "Especial" : "Comum";
            System.out.printf("\n Paciente: %s (CPF: %s , Tipo: %s)\n"), p.getNome() , p.getCpf(), tipo);
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
        }
    }
}
