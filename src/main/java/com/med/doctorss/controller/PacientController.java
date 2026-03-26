package com.med.doctorss.controller;


import com.med.doctorss.entity.pacient.*;
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
@RequestMapping("/pacients")
public class PacientController {

    @Autowired
    private PacientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody DataRegisterPacient data, UriComponentsBuilder uriComponentsBuilder) {
        var pacient = new Pacient(data);
        repository.save(new Pacient(data));

        var uri = uriComponentsBuilder.path("/pacients/{id}").buildAndExpand(pacient.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataDetalhePacient(pacient));
    }

    @GetMapping
    public ResponseEntity<Page<DataListPacient>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        var page = repository.findAllByActiveTrue(pageable).map(DataListPacient::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DataUpdatePacient dataUpdatePacient){
        var pacient = repository.getReferenceById(dataUpdatePacient.id());
        pacient.updateInfos(dataUpdatePacient);

        return ResponseEntity.ok(new DataDetalhePacient(pacient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var pacient = repository.getReferenceById(id);
        pacient.delete();

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var pacient = repository.getReferenceById(id);
        return ResponseEntity.ok(new DataDetalhePacient(pacient));
    }
}