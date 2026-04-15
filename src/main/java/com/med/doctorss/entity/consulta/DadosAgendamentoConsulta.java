package com.med.doctorss.entity.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(Long idDoctor, @NotNull Long idPacient, @NotNull @Future LocalDateTime data) {
}