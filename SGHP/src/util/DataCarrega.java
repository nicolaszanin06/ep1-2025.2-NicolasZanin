package util;

import repository.PacienteRepository;
import repository.MedicoRepository;
import repository.ConsultaRepository;
import repository.InternacaoRepository;
import service.GerenciadorHospitalar;

public final class DataCarrega {

    private static final PacienteRepository repoPac = new PacienteRepository();
    private static final MedicoRepository repoMed = new MedicoRepository();
    private static final ConsultaRepository repoCon = new ConsultaRepository();
    private static final InternacaoRepository repoInt = new InternacaoRepository();

    private DataCarrega() {
    }

    public static void loadAll(GerenciadorHospitalar gh) {
        try {
            // Pacientes
            var pacientes = repoPac.carregar(DataArquivos.arquivo("pacientes.csv"));
            pacientes.forEach(gh::cadastrarPacienteValidador);

            // Médicos
            var medicos = repoMed.carregar(DataArquivos.arquivo("medicos.csv"));
            medicos.forEach(gh::cadastrarMedicoValidador);

            // Consultas
            var consultas = repoCon.carregar(
                    DataArquivos.arquivo("consultas.csv"),
                    cpf -> gh.buscarPacientePorCPF(cpf),
                    crm -> gh.buscarMedicoPorCRM(crm));
            gh.getConsultas().addAll(consultas);

            // Internações
            var internacoes = repoInt.carregar(
                    DataArquivos.arquivo("internacoes.csv"),
                    cpf -> gh.buscarPacientePorCPF(cpf),
                    crm -> gh.buscarMedicoPorCRM(crm));
            gh.getInternacoes().addAll(internacoes);

            System.out.println("Dados carregados de: " + DataArquivos.dir().toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public static void saveAll(GerenciadorHospitalar gh) {
        try {
            repoPac.salvar(gh.getPacientes(), DataArquivos.arquivo("pacientes.csv"));
            repoMed.salvar(gh.getMedicos(), DataArquivos.arquivo("medicos.csv"));
            repoCon.salvar(gh.getConsultas(), DataArquivos.arquivo("consultas.csv"));
            repoInt.salvar(gh.getInternacoes(), DataArquivos.arquivo("internacoes.csv"));

            System.out.println("Dados salvos em: " + DataArquivos.dir().toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}
