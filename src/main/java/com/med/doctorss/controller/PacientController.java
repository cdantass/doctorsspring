package com.med.doctorss.controller;

import com.med.doctorss.entity.pacient.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacients")
public class PacientController {

    private final PacientRepository repository;

    public PacientController(PacientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetalhePacient> register(
            @RequestBody @Valid DataRegisterPacient data,
            UriComponentsBuilder uriComponentsBuilder) {

        var pacient = repository.save(new Pacient(data));

        var uri = uriComponentsBuilder.path("/pacients/{id}").buildAndExpand(pacient.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataDetalhePacient(pacient));
    }

    @GetMapping
    public ResponseEntity<Page<DataListPacient>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAllByActiveTrue(pageable).map(DataListPacient::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDetalhePacient> update(@RequestBody @Valid DataUpdatePacient dataUpdatePacient) {
        var pacient = repository.findById(dataUpdatePacient.id())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        pacient.updateInfos(dataUpdatePacient);
        return ResponseEntity.ok(new DataDetalhePacient(pacient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var pacient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        pacient.delete();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDetalhePacient> detalhar(@PathVariable Long id) {
        var pacient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return ResponseEntity.ok(new DataDetalhePacient(pacient));
    }
}