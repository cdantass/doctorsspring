package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30){
            throw new RuntimeException("Consulta deve ser agendada com 30 minutos de antecedência");
        }
    }
}
