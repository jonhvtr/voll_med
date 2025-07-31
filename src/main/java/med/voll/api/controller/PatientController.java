package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.DataDetailsPatient;
import med.voll.api.domain.dto.DataListPatient;
import med.voll.api.domain.dto.DataPatient;
import med.voll.api.domain.dto.DataUpdatePatient;
import med.voll.api.service.PatientService;
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
public class PatientController {
    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsPatient> cadastrar(@RequestBody @Valid DataPatient dados,
                                                        UriComponentsBuilder uriBuilder) {
        var paciente = patientService.cadastrar(dados);
        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.id()).toUri();
        return ResponseEntity.created(uri).body(paciente);
    }

    @GetMapping
    public ResponseEntity<Page<DataListPatient>> listPacientes(@PageableDefault(size = 10, sort = {"nome"})
                                                                 Pageable pageable) {
        var pacientes = patientService.listarAll(pageable);
        return ResponseEntity.ok().body(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetailsPatient> detailsPaciente(@PathVariable Long id) {
        var paciente = patientService.findPaciente(id);
        return ResponseEntity.ok().body(paciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetailsPatient> atualizar(@RequestBody @Valid DataUpdatePatient dados) {
        var dto = patientService.atualizar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        patientService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
