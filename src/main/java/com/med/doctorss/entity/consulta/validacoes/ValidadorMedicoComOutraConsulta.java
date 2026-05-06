package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.ConsultaRepository;
import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository repository;

    public ValidadorMedicoComOutraConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idDoctor() == null) {
            return;
        }

        boolean medicoPossuiOutraConsultaNoMesmoHorario =
                repository.existsByDoctorIdAndData(dados.idDoctor(), dados.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new IllegalStateException("Médico já possui outra consulta no mesmo horário");
        }
    }
}