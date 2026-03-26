package com.med.doctorss.entity.pacient;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "pacients")
@Entity(name = "pacient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private Boolean active;

    public Pacient(DataRegisterPacient data){
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.active = true;
    }

    public void updateInfos(@Valid DataUpdatePacient dataUpdatePacient) {
        if (dataUpdatePacient.nome() != null) {
            this.nome = dataUpdatePacient.nome();
        }
    }

    public void delete(){
        this.active = false;
    }
}