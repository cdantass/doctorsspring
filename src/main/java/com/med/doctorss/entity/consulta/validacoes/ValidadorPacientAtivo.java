package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import com.med.doctorss.entity.doctor.DoctorRepository;
import com.med.doctorss.entity.pacient.PacientRepository;
import com.med.doctorss.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacientAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacientRepository repository;


    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idPacient() == null){
            return;
        }

        var pacientActive = repository.existsByIdAndActiveTrue(dados.idPacient());
        if (!pacientActive){
            throw new ValidacaoException("Consulta não pode ser agendada com paciente escluído");
        }
    }
}
