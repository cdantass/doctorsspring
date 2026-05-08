package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import com.med.doctorss.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com 30 minutos de antecedência");
        }
    }
}
