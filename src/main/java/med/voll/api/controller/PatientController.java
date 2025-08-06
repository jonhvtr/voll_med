package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
        var uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.id()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @GetMapping
    public ResponseEntity<Page<DataListPatient>> listAllPatient(@PageableDefault(size = 10, sort = {"nome"})
                                                                 Pageable pageable) {
        var patients = patientService.findAll(pageable);
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetailsPatient> detailsPatient(@PathVariable Long id) {
        var patient = patientService.findPatient(id);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetailsPatient> updatePatient(@RequestBody @Valid DataUpdatePatient data) {
        var dto = patientService.update(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
