package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.DataDetailsDoctor;
import med.voll.api.domain.dto.DataListDoctor;
import med.voll.api.domain.dto.DataDoctor;
import med.voll.api.domain.dto.DataUpdateDoctor;
import med.voll.api.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class DoctorController {
    @Autowired
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsDoctor> cadastrar(@RequestBody @Valid DataDoctor dados,
                                                       UriComponentsBuilder uriBuilder) {
        var medico = doctorService.cadastrar(dados);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.id()).toUri();
        return ResponseEntity.created(uri).body(medico);
    }

    @GetMapping
    public ResponseEntity<Page<DataListDoctor>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = doctorService.listarAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetailsDoctor> detailsMedico(@PathVariable Long id) {
        var medicoUrl = doctorService.findDoctor(id);
        return ResponseEntity.ok(medicoUrl);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetailsDoctor> atualizar(@RequestBody @Valid DataUpdateDoctor dados) {
        var dto = doctorService.atualizar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        doctorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
