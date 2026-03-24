package com.med.doctorss.doctor;

import com.med.doctorss.address.Address;

public record DataDetalheDoctor(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Address address) {

    public DataDetalheDoctor(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(),doctor.getEspecialidade(), doctor.getAddress());
    }
}
