package com.med.doctorss.entity.consulta.validacoes;

import com.med.doctorss.entity.consulta.ConsultaRepository;
import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacientSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientPossuiOutraConsultaNoDia = repository.existsByPacientIdAndDataBetween(dados.idPacient(), primeiroHorario, ultimoHorario);
        if (pacientPossuiOutraConsultaNoDia){
            throw new IllegalStateException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
