package Controlador;

import Relatorios.RelatoriosServico;
import Servicos.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPrincipal {
    private ConsultaServico consultaServico;
    private InternacaoServico internacaoServico;
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;
    private PlanoSaudeServico planoSaudeServico;
    private RelatoriosServico relatoriosServico;

    private Scanner scanner;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MenuPrincipal(){
        this.scanner = new Scanner(System.in);
        this.medicoServico = new MedicoServico();
        this.pacienteServico = new PacienteServico();
        this.planoSaudeServico = new PlanoSaudeServico();
        this.consultaServico = new ConsultaServico(medicoServico,pacienteServico);
        this.internacaoServico = new InternacaoServico(pacienteServico,medicoServico);
        this.relatoriosServico = new RelatoriosServico(pacienteServico,medicoServico,consultaServico,internacaoServico);
    }
//loop do menu
    public void iniciar(){
        int opcao;
        do {
            exibirMenuPrincipal();
                if (scanner.hasNextInt()){
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                }else{
                    System.out.println("Opção inválida. Digite um número.");
                    scanner.nextLine();
                    opcao = 0;
                }
                switch (opcao){
                    case 1:
                        menuCadastro();
                        break;
                    case 2:
                        menuAgendamentoInternacao();
                        break;
                    case 3:
                        menuRelatorios();
                        break;
                    case 4:
                        System.out.println("Encerrando o sistema. Dados salvos automaticamente.");
                        break;
                    default:
                        if (opcao!= 4 ){
                            System.out.println("Opção não reconhecida. Tente novamente!");
                        }
                        break;
                }
            }
            while (opcao != 4);
        }
    }
//metodo para exibir o menu principal
private void exibirMenuPrincipal(){
    System.out.println("\n=================================");
    System.out.println("  HOSPITAL POO - Menu Principal  ");
    System.out.println("=================================");
    System.out.println("1. Cadastros (Médicos, Pacientes e Planos)");
    System.out.println("2. Agendamentos e Internações");
    System.out.println("3. Relatórios");
    System.out.println("4. Sair");
    System.out.println("Escolha uma opção: ");
}
//menu cadastro
