package com.med.doctorss.entity.doctor;

import com.med.doctorss.entity.address.DataAddress;

public record DataRegisterDoc(String nome, String email, String crm, String telefone, Especialidade especialidade, DataAddress address) {
}
