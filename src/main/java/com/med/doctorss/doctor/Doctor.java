package com.med.doctorss.doctor;

import com.med.doctorss.address.Address;
import com.med.doctorss.address.DataAddress;
import jakarta.persistence.*;
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
    private String telefone; //

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Address address;

    public Doctor(DataRegisterDoc data){
        this.nome = data.nome();
        this.email = data.email();
        this.crm = data.crm();
        this.telefone = data.telefone();
        this.especialidade = data.especialidade();
        this.address = new Address(data.address());
    }
}