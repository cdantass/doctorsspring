package com.med.doctorss.entity.doctor;

import com.med.doctorss.entity.address.DataAddress;
import jakarta.validation.constraints.NotNull;

public record DataUpdateDoctor(@NotNull Long id, String nome, String telefone, DataAddress address) {
}
