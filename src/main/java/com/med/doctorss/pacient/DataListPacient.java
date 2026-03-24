package com.med.doctorss.pacient;

public record DataListPacient(Long id, String nome, String email, String telefone) {

    public DataListPacient(Pacient pacient){
        this(pacient.getId(), pacient.getNome(), pacient.getEmail(), pacient.getTelefone());
    }
}
