package med.voll.api.service;

import med.voll.api.domain.Medico;
import med.voll.api.dto.DadosListMedico;
import med.voll.api.dto.DadosMedico;
import med.voll.api.dto.DadosUpdateMedico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void cadastrar(DadosMedico dados) {
        medicoRepository.save(new Medico(dados));
    }

    public Page<DadosListMedico> listarAll(Pageable pageable) {
        return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListMedico::new);
    }

    public void atualizar(DadosUpdateMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    public void excluir(Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
