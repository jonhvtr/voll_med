package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosDetalhamentoPaciente;
import med.voll.api.domain.dto.DadosListPaciente;
import med.voll.api.domain.dto.DadosPaciente;
import med.voll.api.domain.dto.DadosUpdatePaciente;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosPaciente dados,
                                                               UriComponentsBuilder uriBuilder) {
        var paciente = pacienteService.cadastrar(dados);
        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.id()).toUri();
        return ResponseEntity.created(uri).body(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListPaciente>> listPacientes(@PageableDefault(size = 10, sort = {"nome"})
                                                                 Pageable pageable) {
        var pacientes = pacienteService.listarAll(pageable);
        return ResponseEntity.ok().body(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detailsPaciente(@PathVariable Long id) {
        var paciente = pacienteService.findPaciente(id);
        return ResponseEntity.ok().body(paciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosUpdatePaciente dados) {
        var dto = pacienteService.atualizar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
