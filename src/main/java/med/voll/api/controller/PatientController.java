package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.*;
import med.voll.api.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PatientController {
    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsPatient> createPatient(@RequestBody @Valid DataPatient data,
                                                        UriComponentsBuilder uriBuilder) {
        var patient = patientService.create(data);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.id()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping
    public ResponseEntity<Page<DataListPatient>> listAllPatient(@PageableDefault(size = 10, sort = {"nome"})
                                                                 Pageable pageable) {
        var patients = patientService.findAll(pageable);
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/especifico")
    public ResponseEntity<DataDetailsPatient> findByNamePatient(@RequestBody DataNamePatient data) {
        var patient = patientService.findByNamePatient(data);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetailsPatient> detailsPatient(@PathVariable UUID id) {
        var patient = patientService.findPatient(id);
        return ResponseEntity.ok().body(patient);
    }

    @GetMapping("/desativado")
    public ResponseEntity<Page<DataListPatient>> findReactivatePatiente(@PageableDefault(size = 10, sort = {"nome"})
                                                                            Pageable pageable) {
        var patients = patientService.findDisabledPatient(pageable);
        return ResponseEntity.ok().body(patients);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetailsPatient> updatePatient(@RequestBody @Valid DataUpdatePatient data) {
        var dto = patientService.update(data);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/reativar")
    @Transactional
    public ResponseEntity<?> reactivatePatient(@RequestBody DataReactivateRequest data) {
        patientService.reactivatePatient(data.id());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
