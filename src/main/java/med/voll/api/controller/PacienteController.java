package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.DadosListPaciente;
import med.voll.api.dto.DadosPaciente;
import med.voll.api.dto.DadosUpdatePaciente;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosPaciente dados) {
        pacienteService.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosListPaciente> listPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return pacienteService.listarAll(pageable);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosUpdatePaciente dados) {
        pacienteService.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
    }
}
