package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosListMedico;
import med.voll.api.dto.DadosMedico;
import med.voll.api.dto.DadosUpdateMedico;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosMedico dados) {
        medicoService.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosListMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return medicoService.listarAll(pageable);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosUpdateMedico dados) {
        medicoService.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        medicoService.excluir(id);
    }
}
