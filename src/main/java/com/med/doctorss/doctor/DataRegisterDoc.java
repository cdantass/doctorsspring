package com.med.doctorss.doctor;

import com.med.doctorss.address.DataAddress;

public record DataRegisterDoc(String nome, String email, String crm, String telefone, Especialidade especialidade, DataAddress address) {
}
