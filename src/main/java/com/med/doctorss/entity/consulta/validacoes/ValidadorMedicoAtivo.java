package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import com.med.doctorss.entity.doctor.DoctorRepository;
import com.med.doctorss.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private DoctorRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idDoctor() == null) {
            return;
        }

        boolean medicoAtivo = repository.existsByIdAndActiveTrue(dados.idDoctor());

        if (!medicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo ou inexistente");
        }
    }
}