package com.med.doctorss.entity.doctor;

public record DataListDoctors(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DataListDoctors(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
