package Controlador;

import Relatorios.RelatoriosServico;
import Servicos.*;
import entidades.internacao.Internacao;
import entidades.plano.PlanoSaude;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class MenuPrincipal {
    private MedicoServico medicoServico;
    private PacienteServico pacienteServico;
    private PlanoSaudeServico planoSaudeServico;
    private ConsultaServico consultaServico;
    private InternacaoServico internacaoServico;
    private RelatoriosServico relatoriosServico;
    //deixar o menu visual primário mais bonito
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN_LIGHT = "\u001B[96m";  //Ciano
    public static final String ANSI_WHITE = "\u001B[97m";      // Branco/Cinza
    public static final String ANSI_BLUE = "\u001B[34m";       // voltar ao padrao para a cor nao continuar
    public static final String ANSI_YELLOW = "\u001B[33m"; // amarelo para sair


    private Scanner scanner;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MenuPrincipal(){
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.medicoServico = new MedicoServico();
        this.pacienteServico = new PacienteServico();
        this.planoSaudeServico = new PlanoSaudeServico();

        this.consultaServico = new ConsultaServico(medicoServico,pacienteServico);
        this.internacaoServico = new InternacaoServico(pacienteServico,medicoServico);

        this.relatoriosServico = new RelatoriosServico(pacienteServico,medicoServico,consultaServico,internacaoServico);
    }
    //loop do menu
    public void inicar(){
        //para exibir as mensagens de carregamento de repositorios no lugar correto
        System.out.println("\n\n"); // Mais espaço para afastar
        System.out.println(ANSI_CYAN_LIGHT + "=============================================");
        System.out.println("--- STATUS DE INICIALIZAÇÃO CONCLUÍDO ---" + ANSI_RESET);
        System.out.println(ANSI_CYAN_LIGHT + "=============================================" + ANSI_RESET);

        int opcao;
        do {
            exibirMenuPrincipal();
            // Lógica para garantir que o usuário digite um número
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
        System.out.println(ANSI_CYAN_LIGHT + "\n=================================");
        System.out.println("  HOSPITAL POO - Menu Principal  " + ANSI_RESET);
        System.out.println(ANSI_CYAN_LIGHT + "=================================");
        System.out.println(ANSI_WHITE + "1. Cadastros (Médicos, Pacientes, Planos)" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "2. Agendamentos e Internações" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "3. Relatórios" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "4. Sair" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Escolha uma opção: " + ANSI_RESET);
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
    private void cadastrarPlanoSaude(){
        System.out.println("\n--- Cadastrar Plano de Saúde ---");
        System.out.println("Nome do Plano: ");String nomePlano = scanner.nextLine().trim();
        System.out.println("Desconto para idosos (ex: 0.15 para 15%): "); String descontoStr = scanner.nextLine().trim();
        System.out.println("È um Plano Especial? (1- Sim ; 2- Não): ");
        double descontoIdoso = 0.0;
        boolean isPlanoEspecial = false;
        int planoEspecialInt;
        try{
            if (scanner.hasNextInt()){
                planoEspecialInt = scanner.nextInt();
            }else{
                throw new NumberFormatException("Opção de plano deve ser um número!(1 ou 2)");
            }
            scanner.nextLine();
            descontoIdoso = Double.parseDouble(descontoStr);
            isPlanoEspecial = (planoEspecialInt == 1);

        }catch (NumberFormatException e){
            System.err.println("Erro: O valor do desconto deve ser um número válido(use o ponto decimal). Operação cancelada!");
            return;
        }
        if(planoEspecialInt != 1 && planoEspecialInt != 2){
            System.err.println("Erro: Opção de plano especial deve ser 1 ou 2. ");
            return;
        }

        planoSaudeServico.cadastrarPlano(nomePlano,descontoIdoso,isPlanoEspecial);
        System.out.println("Plano de Saúde " + nomePlano + "cadastrado com sucesso!");
    }
    //menu e metodos de agendamento
    private void menuAgendamentoInternacao(){
        int opcao;
        do {
            System.out.println("\n--- Menu Agendamento e Internações ---");
            System.out.println("1. Agendar Consulta");
            System.out.println("2. Internar Paciente");
            System.out.println("3. Dar alta a Paciente");
            System.out.println("4. Voltar ao Menu Prinicipal");

            if (scanner.hasNextInt()){
                opcao = scanner.nextInt();
                scanner.nextLine();
            }else{
                System.out.println("Opção inválida!");
                scanner.nextLine();
                opcao = 0;
            }
            switch (opcao){
                case 1: agendarConsulta(); break;
                case 2: internarPaciente(); break;
                case 3: darAltaPaciente(); break;
                case 4: concluirConsultaUI(); break;
                case 5: break;
                default:
                    System.out.println("Opção inválida!"); break;
            }
        } while (opcao != 5);
    }
    private void concluirConsultaUI(){
        System.out.println("\n--- Concluir consulta E Registro de Diagnóstico ---");
        System.out.print("CRM do Médico: ");
        String medicoCrm = scanner.nextLine().trim();
        System.out.print("Data e Hora da Consulta (dd/MM/yyyy HH:mm): ");
        String dataHoraStr = scanner.nextLine().trim();
        System.out.print("Diagnóstico: ");
        String diagnostico = scanner.nextLine().trim();
        System.out.print("Prescrição de Medicamentos: ");
        String prescricao = scanner.nextLine().trim();
        try {
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DATE_TIME_FORMATTER);
            consultaServico.concluirConsulta(medicoCrm,dataHora,diagnostico,prescricao);
        }catch (Exception e){
            System.err.println("Erro: Verifique o formato da data/hora(dd/MM/yyyy HH:mm)!");
        }
    }
// agendar a consulta
private void agendarConsulta(){
    System.out.println("\n--- Agendamento de Consulta ---");
    System.out.println("CPF do paciente: "); String pacienteCpf = scanner.nextLine();
    System.out.println("CRM do médico: "); String medicoCrm = scanner.nextLine();
    System.out.println("Data e hora (dd/MM/yyyy HH:mm): "); String dataHoraStr = scanner.nextLine();
    System.out.println("Local da Consulta: "); String local = scanner.nextLine();

    try{
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DATE_TIME_FORMATTER);
        consultaServico.agendarConsulta(pacienteCpf,medicoCrm,dataHora,local);
    }catch (Exception e){
        System.err.println("Erro de formatação de data/hora. Use o formato dd/MM/yyyy HH:mm");
    }
}
//internar o paciente
    private void internarPaciente(){
        System.out.println("\n--- Internação do Paciente ---");
        System.out.println("CPF do paciente: "); String pacienteCpf = scanner.nextLine();
        System.out.println("CRM do médico responsável: "); String medicoCrm = scanner.nextLine();;
        System.out.println("Número do Quarto: "); int numeroQuarto = scanner.nextInt();scanner.nextLine();

        internacaoServico.internarPaciente(pacienteCpf,medicoCrm,numeroQuarto,LocalDateTime.now());
    }
//dar alta ao paciente
    private void darAltaPaciente(){
        System.out.println("\n--- Alta do Paciente ---");
        System.out.println("CPF do paciente que receberá alta: ");String pacienteCpf = scanner.nextLine();
        Internacao internacaoAtiva = internacaoServico.buscarInternacaoAtivaPorCpf(pacienteCpf);
        if (internacaoAtiva == null){
            System.err.println("Erro: Paciente não tem internacao ativa!");
            return;
        }
        System.out.println("Custo base da diária de internação: ");
        double custoBase = scanner.nextDouble();
        scanner.nextLine();

        internacaoServico.darAlta(internacaoAtiva,custoBase);
        System.out.println("Alta realizada com sucesso!! Custo final calculado!");
    }
//menu + metodos do relatorio
    private void menuRelatorios(){
        int opcao;
        do {
            System.out.println("\n--- Menu de Relatórios ---");
            System.out.println("1. Histórico completo dos pacientes ");
            System.out.println("2. Pacientes internados atualmente ");
            System.out.println("3. Médico que mais atendeu ");
            System.out.println("4. Lista completa de Médicos");
            System.out.println("5. Voltar ao Menu Principal");
            if (scanner.hasNextInt()){
                opcao = scanner.nextInt();
                scanner.nextLine();
            }else {
                System.out.println("Opção inválida!");
                scanner.nextLine();
                opcao=0;
            }
            switch (opcao){
                case 1: relatoriosServico.relatorioPacienteComHistorico();break;
                case 2: relatoriosServico.relatorioPacientesInternado();break;
                case 3: relatoriosServico.relatorioMedicoMaisAtendeu();break;
                case 4: relatoriosServico.relatorioListarMedicos();break;
                case 5: relatorioConsultasPorStatusUI();break;
                case 6: relatoriosServico.relatorioEstatisticoPlanoSaude();break;
                case 7: break;
                default:
                    System.out.println("Opção inválida!");
            }
        }while (opcao != 7);
    }
    private void relatorioConsultasPorStatusUI(){
        System.out.println("\n--- Filtro de Consultas ---");
        System.out.println("1. Consultas agendadas(futuras)");
        System.out.println("2. Consultas Concluidas(passadas)");
        System.out.println("Escolha o tipo de consulta: ");
        int opcao;
        if (scanner.hasNextInt()){
            opcao = scanner.nextInt();
            scanner.nextLine();
        }else{
            scanner.nextLine();
            System.err.println("Opção inválida!");
            return;
        }
        String status = "";
        if (opcao == 1){
            status = "Agendada";
        }else if (opcao == 2){
            status = "Concluida";
        }else {
            System.err.println("Opção não reconhecida!");
            return;
        }
        relatoriosServico.relatorioConsultasStatus(status);
    }

}

