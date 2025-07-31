package med.voll.api.service;

import med.voll.api.domain.Medico;
import med.voll.api.domain.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.dto.DadosListMedico;
import med.voll.api.domain.dto.DadosMedico;
import med.voll.api.domain.dto.DadosUpdateMedico;
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

    public DadosDetalhamentoMedico cadastrar(DadosMedico dados) {
        var medico = new Medico(dados);
        medicoRepository.save(medico);

        return new DadosDetalhamentoMedico(medico);
    }

    public Page<DadosListMedico> listarAll(Pageable pageable) {
        return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListMedico::new);
    }

    public DadosDetalhamentoMedico findDoctor(Long id) {
        var medico = medicoRepository.getReferenceById(id);

        return new DadosDetalhamentoMedico(medico);
    }

    public DadosDetalhamentoMedico atualizar(DadosUpdateMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return new DadosDetalhamentoMedico(medico);
    }

    public void excluir(Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
