package com.med.doctorss.controller;

import com.med.doctorss.entity.doctor.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody DataRegisterDoc data, UriComponentsBuilder uriComponentsBuilder) {
        var doctor = repository.save(new Doctor(data));

        var uri = uriComponentsBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetalheDoctor(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DataListDoctors>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByActiveTrue(paginacao).map(DataListDoctors::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DataUpdateDoctor dataupdate) {
        var doctor = repository.findById(dataupdate.id())
                .orElseThrow(() -> new EntityNotFoundException());
        doctor.updateInfo(dataupdate);

        return ResponseEntity.ok(new DataDetalheDoctor(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var doctor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        doctor.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var doctor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return ResponseEntity.ok(new DataDetalheDoctor(doctor));
    }
}