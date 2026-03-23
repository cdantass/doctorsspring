package com.med.doctorss.doctor;

import com.med.doctorss.address.DataAddress;
import jakarta.validation.constraints.NotNull;

public record DataUpdateDoctor(@NotNull Long id, String nome, String telefone, DataAddress address) {
}
