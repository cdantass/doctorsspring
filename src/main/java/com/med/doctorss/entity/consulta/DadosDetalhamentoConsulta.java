package com.med.doctorss.entity.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idDoctor, Long idPacient, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consultaSalva) {
        this(consultaSalva.getId(), consultaSalva.getDoctor().getId(), consultaSalva.getPacient().getId(), consultaSalva.getData());
    }
}