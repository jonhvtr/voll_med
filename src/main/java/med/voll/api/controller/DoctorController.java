package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.*;
import med.voll.api.service.DoctorService;
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
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {
    @Autowired
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsDoctor> createDoctor(@RequestBody @Valid DataDoctor data,
                                                          UriComponentsBuilder uriBuilder) {
        var doctor = doctorService.create(data);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(doctor.id()).toUri();
        return ResponseEntity.created(uri).body(doctor);
    }

    @GetMapping
    public ResponseEntity<Page<DataListDoctor>> listAllDoctor(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = doctorService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/desativado")
    public ResponseEntity<Page<DataListDoctor>> listDisabledDoctor(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = doctorService.findDisableDoctor(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetailsDoctor> detailsDoctor(@PathVariable UUID id) {
        var doctorUrl = doctorService.findDoctor(id);
        return ResponseEntity.ok(doctorUrl);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetailsDoctor> udpateDoctor(@RequestBody @Valid DataUpdateDoctor data) {
        var dto = doctorService.update(data);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/reativar")
    @Transactional
    public ResponseEntity<?> reactivateDoctor(@RequestBody DataReactivateRequest data) {
        doctorService.reactivate(data.id());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteDoctor(@PathVariable UUID id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
