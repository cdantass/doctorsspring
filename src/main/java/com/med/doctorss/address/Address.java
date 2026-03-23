package com.med.doctorss.address;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Address(DataAddress data) {
        this.logradouro = data.logradouro();
        this.bairro = data.bairro();
        this.cep = data.cep();
        this.uf = data.uf();
        this.cidade = data.cidade();
        this.numero = data.numero();
        this.complemento = data.complemento();
    }

    public void updateInfo(DataAddress address) {
        if (address.logradouro() != null){
            this.logradouro = address.logradouro();
        }
        if (address.bairro() != null){
            this.bairro = address.bairro();
        }
        if (address.cep() != null){
            this.cep = address.cep();
        }
        if (address.uf() != null){
            this.uf = address.uf();
        }
        if (address.cidade() != null){
            this.cidade = address.cidade();
        }
        if (address.numero() != null){
            this.numero = address.numero();
        }
        if (address.complemento() != null){
            this.complemento = address.complemento();
        }
    }
}
