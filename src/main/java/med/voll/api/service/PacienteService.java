package med.voll.api.service;

import med.voll.api.domain.Paciente;
import med.voll.api.dto.DadosListPaciente;
import med.voll.api.dto.DadosPaciente;
import med.voll.api.dto.DadosUpdatePaciente;
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

    public void cadastrar(DadosPaciente dados) {
        pacienteRepository.save(new Paciente(dados));
    }

    public Page<DadosListPaciente> listarAll(Pageable pageable) {
        return pacienteRepository.findByAtivoTrue(pageable).map(DadosListPaciente::new);
    }

    public void atualizar(DadosUpdatePaciente dados) {
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarDados(dados);
    }

    public void excluir(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }
}
