package Controlador;

import Relatorios.RelatoriosServico;
import Servicos.*;
import entidades.plano.PlanoSaude;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPrincipal {
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;
    private PlanoSaudeServico planoSaudeServico;
    private ConsultaServico consultaServico;
    private InternacaoServico internacaoServico;
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

        this.relatoriosServico = new RelatoriosServico(pacienteServico,medicoServico,internacaoServico);
    }
    //loop do menu
    public void inicar(){
        int opcao;
        do {
            exibirMenuPrincipal();
            // Lógica para garantir que o usuário digitou um número
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opção inválida. Digite um número.");
                scanner.nextLine();
                opcao = 0;
            }

            switch (opcao) {
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
                    if (opcao != 4) {
                        System.out.println("Opção não reconhecida. Tente novamente.");
                    }
                    break;
            }
        } while (opcao != 4);
    }
    //menu principal exibir
    private void exibirMenuPrincipal(){
        System.out.println("\n=================================");
        System.out.println("  HOSPITAL POO - Menu Principal  ");
        System.out.println("=================================");
        System.out.println("1. Cadastros (Médicos, Pacientes, Planos)");
        System.out.println("2. Agendamentos e Internações");
        System.out.println("3. Relatórios");
        System.out.println("4. Sair");
        System.out.println("Escolha uma opção: ");
    }
    //menu cadastro
    private void menuCadastro(){
        int opcao;
        do {
            System.out.println("\n--- Menu de Cadastros ---");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Cadastrar Paciente Comum");
            System.out.println("3. Cadastrar Paciente Especial");
            System.out.println("4. Cadastrar Plano de Saúde");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.println("Escolha uma opção: ");

            if (scanner.hasNextInt()){
                opcao = scanner.nextInt();
                scanner.nextLine();
            }else{
                System.out.println("Opção inválida!");
                scanner.nextLine();
                opcao = 0;
            }
            switch (opcao){
                case 1: cadastrarMedico(); break;
                case 2: cadastrarPacienteComum(); break;
                case 3: cadastrarPacienteEspecial();break;
                case 4: cadastrarPlanoSaude();break;
                case 5: break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }while (opcao!=5);
    }
//cadastrar medico
    private void cadastrarMedico(){
        System.out.println("\n--- Cadastro de Médico ---");
        System.out.println("Nome: "); String nome = scanner.nextLine();
        System.out.println("CPF: "); String cpf = scanner.nextLine();
        System.out.println("Idade: "); int idade = scanner.nextInt();scanner.nextLine();
        System.out.println("CRM: "); String crm = scanner.nextLine();
        System.out.println("Especialidade: "); String especialidade = scanner.nextLine();
        System.out.println("Custo da Consulta: "); double custoConsulta = scanner.nextDouble();scanner.nextLine();
        medicoServico.cadastrarMedicos(nome,cpf,idade,crm,especialidade,custoConsulta);
    }
//cadastrar paciente comum
    private void cadastrarPacienteComum(){
        System.out.println("\n--- Cadastro Paciente Comum ---");
        System.out.println("Nome: "); String nome = scanner.nextLine();
        System.out.println("CPF: "); String cpf = scanner.nextLine();
        System.out.println("Idade: "); int idade = scanner.nextInt();scanner.nextLine();
        pacienteServico.cadastrarPacienteComum(nome,cpf,idade);
    }
//cadastrar paciente especial
    private void cadastrarPacienteEspecial(){
        System.out.println("\n--- Cadastro Paciente Especial ---");
        System.out.println("Nome: "); String nome = scanner.nextLine();
        System.out.println("CPF: "); String cpf = scanner.nextLine();
        System.out.println("Idade: "); int idade = scanner.nextInt();scanner.nextLine();
        System.out.println("Nome do plano de saúde: "); String nomePlano = scanner.nextLine();

        PlanoSaude plano = planoSaudeServico.buscarPlanoPorNome(nomePlano);
        if (plano == null){
            System.err.println("Erro: Plano de Saúde '" + nomePlano + "' não encontrado. Cadastre-o primeiro!");
            return;
        }
        pacienteServico.cadastrarPacienteEspecial(nome,cpf,idade,plano);
    }
//cadastrar plano de saude
}

