package com.med.doctorss.entity.pacient;

import jakarta.validation.constraints.NotNull;

public record DataUpdatePacient(@NotNull Long id, String nome, String telefone) {
}
