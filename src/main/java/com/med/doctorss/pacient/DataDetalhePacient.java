package com.med.doctorss.pacient;

public record DataDetalhePacient(Long id, String nome, String email, String telefone) {

    public DataDetalhePacient(Pacient pacient){
        this(pacient.getId(), pacient.getNome(), pacient.getEmail(), pacient.getTelefone());
    }
}
