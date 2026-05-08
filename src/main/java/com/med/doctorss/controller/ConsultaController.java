package com.med.doctorss.controller;

import com.med.doctorss.entity.consulta.AgendaDeConsultas;
import com.med.doctorss.entity.consulta.ConsultaRepository;
import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import com.med.doctorss.infra.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agender(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var detalhe = agenda.agendar(dados);
        return ResponseEntity.ok(detalhe);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var consulta = consultaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Essa consulta não existe"));
        agenda.cancelar(id);

        return ResponseEntity.noContent().build();
    }
}
