package com.med.doctorss.entity.doctor;

import com.med.doctorss.entity.address.Address;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "doctors")
@Entity(name = "doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Address address;

    public Doctor(DataRegisterDoc data){
        this.active = true;
        this.nome = data.nome();
        this.email = data.email();
        this.crm = data.crm();
        this.telefone = data.telefone();
        this.especialidade = data.especialidade();
        this.address = new Address(data.address());
    }

    public void updateInfo(@Valid DataUpdateDoctor dataupdate) {
        if (dataupdate.nome() != null){
            this.nome = dataupdate.nome();
        }
        if (dataupdate.telefone() != null){
            this.telefone = dataupdate.telefone();
        }
        if (dataupdate.address() != null){
            this.address.updateInfo(dataupdate.address());
        }
    }
    public void delete() {
        this.active = false;
    }
}