package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.*;
import model.enums.StatusConsulta;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuRelatorios {

        private final Scanner sc;
        private final GerenciadorHospitalar gh;
        private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        public MenuRelatorios(Scanner sc, GerenciadorHospitalar gh) {
                this.sc = sc;
                this.gh = gh;
        }

        public void exibirMenu() {
                while (true) {
                        System.out.println("""
                                        ------ Relatórios ------
                                        1. Pacientes
                                        2. Médicos
                                        3. Consultas
                                        4. Internações ativas
                                        5. Estatísticas gerais
                                        6. Planos de saúde
                                        7. Voltar
                                        ------------------------""");
                        int op = Utilitario.lerInt(sc, "> Opção: ");
                        if (op == 7)
                                break;
                        switch (op) {
                                case 1 -> pacientesHistorico();
                                case 2 -> medicosResumo();
                                case 3 -> consultasFiltro();
                                case 4 -> internacoesAtivas();
                                case 5 -> estatisticasGerais();
                                case 6 -> planosSaude();
                                default -> System.out.println("Opção inválida!");
                        }
                }
        }

        // 1) Pacientes com histórico
        private void pacientesHistorico() {
                gh.getPacientes().forEach(p -> {
                        System.out.println("• " + p.getNome() + " (CPF " + p.getCpf() + ")");
                        var consultas = gh.getConsultas().stream()
                                        .filter(c -> c.getPaciente().equals(p)).toList();
                        var internacoes = gh.getInternacoes().stream()
                                        .filter(i -> i.getPaciente().equals(p)).toList();
                        System.out.println("   - Consultas: " + consultas.size());
                        consultas.forEach(c -> System.out.println("     " + c.getDataHora().format(FMT)
                                        + " | " + c.getMedico().getNome() + " | "
                                        + c.getStatus().getStatusFormatado()));
                        System.out.println("   - Internações: " + internacoes.size());
                        internacoes.forEach(i -> System.out.println("     " + i.getDataInternacao().format(FMT)
                                        + " | Quarto " + i.getNumeroQuarto() + " | Ativa=" + i.estaAtiva()));
                });
        }

        // 2) Médicos
        private void medicosResumo() {
                gh.getMedicos().forEach(m -> {
                        var consultas = gh.getConsultas().stream()
                                        .filter(c -> c.getMedico().equals(m)).toList();
                        long realizadas = consultas.stream()
                                        .filter(c -> c.getStatus() == StatusConsulta.REALIZADA).count();
                        System.out.println("• " + m.getNome() + " — " + m.getEspecialidade()
                                        + " | Realizadas: " + realizadas);
                        consultas.stream()
                                        .filter(c -> c.getDataHora().isAfter(LocalDateTime.now()))
                                        .sorted(Comparator.comparing(Consulta::getDataHora))
                                        .forEach(c -> System.out.println("   " + c.getDataHora().format(FMT)
                                                        + " | " + c.getPaciente().getNome()));
                });
        }

        // 3) Consultas futuras
        private void consultasFiltro() {
                System.out.println("""
                                Filtro:
                                1) CPF paciente
                                2) CRM médico
                                3) Especialidade
                                4) Todas""");
                int f = Utilitario.lerInt(sc, "> Opção: ");
                Predicate<Consulta> filtro = switch (f) {
                        case 1 -> {
                                String cpf = Utilitario.lerString(sc, "CPF: ");
                                yield c -> c.getPaciente().getCpf().equals(cpf);
                        }
                        case 2 -> {
                                String crm = Utilitario.lerString(sc, "CRM: ");
                                yield c -> c.getMedico().getCrm().equals(crm);
                        }
                        case 3 -> {
                                String esp = Utilitario.lerString(sc, "Especialidade: ").toLowerCase();
                                yield c -> c.getMedico().getEspecialidade().toLowerCase().contains(esp);
                        }
                        default -> c -> true;
                };

                var base = gh.getConsultas().stream().filter(filtro).toList();
                LocalDateTime agora = LocalDateTime.now();
                var futuras = base.stream().filter(c -> c.getDataHora().isAfter(agora)).toList();
                var passadas = base.stream().filter(c -> c.getDataHora().isBefore(agora)).toList();
                System.out.println("Futuras: " + futuras.size());
                futuras.forEach(this::printConsulta);
                System.out.println("Passadas: " + passadas.size());
                passadas.forEach(this::printConsulta);
        }

        // 4) Internações ativas
        private void internacoesAtivas() {
                var ativas = gh.getInternacoes().stream().filter(Internacao::estaAtiva).toList();
                if (ativas.isEmpty()) {
                        System.out.println("Nenhuma internação ativa.");
                        return;
                }
                ativas.forEach(i -> System.out.println("• " + i.getPaciente().getNome()
                                + " | Quarto " + i.getNumeroQuarto()
                                + " | Dias=" + i.getTempoDeInternacao()));
        }

        // 5) Estatísticas gerais
        private void estatisticasGerais() {
                System.out.println("--- Estatísticas ---");
                var porMedico = gh.getConsultas().stream()
                                .filter(c -> c.getStatus() == StatusConsulta.REALIZADA)
                                .collect(Collectors.groupingBy(c -> c.getMedico().getNome(), Collectors.counting()));
                porMedico.entrySet().stream().max(Map.Entry.comparingByValue())
                                .ifPresent(e -> System.out.println("• Médico que mais atendeu: " + e.getKey() + " ("
                                                + e.getValue() + ")"));
                var porEsp = gh.getConsultas().stream()
                                .collect(Collectors.groupingBy(c -> c.getMedico().getEspecialidade(),
                                                Collectors.counting()));
                porEsp.entrySet().stream().max(Map.Entry.comparingByValue())
                                .ifPresent(e -> System.out.println("• Especialidade mais procurada: " + e.getKey()
                                                + " (" + e.getValue() + ")"));
        }

        // 6) Planos (qtd e economia)
        private void planosSaude() {
                var pacientesEsp = gh.getPacientes().stream()
                                .filter(p -> p instanceof PacienteEspecial)
                                .map(p -> (PacienteEspecial) p).toList();
                if (pacientesEsp.isEmpty()) {
                        System.out.println("Nenhum paciente com convênio.");
                        return;
                }
                System.out.println("• Quantidade por plano:");
                pacientesEsp.stream()
                                .collect(Collectors.groupingBy(p -> p.getPlanoSaude().getNome(), Collectors.counting()))
                                .forEach((plano, qtd) -> System.out.println("  " + plano + ": " + qtd));
                System.out.println("• Economia total por plano (R$):");
                gh.getConsultas().stream()
                                .filter(c -> c.getStatus() == StatusConsulta.REALIZADA
                                                && c.getPaciente() instanceof PacienteEspecial)
                                .collect(Collectors.groupingBy(
                                                c -> ((PacienteEspecial) c.getPaciente()).getPlanoSaude().getNome(),
                                                Collectors.summingDouble(c -> {
                                                        double cheio = c.getMedico().getCustoConsulta();
                                                        double pago = ((PacienteEspecial) c.getPaciente())
                                                                        .custoDaConsulta(c.getMedico());
                                                        return Math.max(0, cheio - pago);
                                                })))
                                .forEach((plano, valor) -> System.out
                                                .println("  " + plano + ": " + String.format("%.2f", valor)));
        }

        // Helper
        private void printConsulta(Consulta c) {
                System.out.println("  " + c.getDataHora().format(FMT)
                                + " | " + c.getLocal()
                                + " | " + c.getPaciente().getNome()
                                + " | " + c.getMedico().getNome()
                                + " | " + c.getStatus().getStatusFormatado());
        }
}
