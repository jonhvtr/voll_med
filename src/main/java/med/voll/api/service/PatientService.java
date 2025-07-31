package med.voll.api.service;

import med.voll.api.domain.Paciente;
import med.voll.api.domain.dto.DadosDetalhamentoPaciente;
import med.voll.api.domain.dto.DadosListPaciente;
import med.voll.api.domain.dto.DadosPaciente;
import med.voll.api.domain.dto.DadosUpdatePaciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    @Autowired
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public DadosDetalhamentoPaciente cadastrar(DadosPaciente dados) {
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        return new DadosDetalhamentoPaciente(paciente);
    }

    public Page<DadosListPaciente> listarAll(Pageable pageable) {
        return pacienteRepository.findByAtivoTrue(pageable).map(DadosListPaciente::new);
    }

    public DadosDetalhamentoPaciente findPaciente(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return new DadosDetalhamentoPaciente(paciente);
    }

    public DadosDetalhamentoPaciente atualizar(DadosUpdatePaciente dados) {
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarDados(dados);
        return new DadosDetalhamentoPaciente(paciente);
    }

    public void excluir(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }
}
