package com.med.doctorss.entity.consulta;

import com.med.doctorss.entity.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.med.doctorss.entity.doctor.Doctor;
import com.med.doctorss.entity.doctor.DoctorRepository;
import com.med.doctorss.entity.pacient.PacientRepository;
import com.med.doctorss.infra.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRepository consultaRepository;
    private final DoctorRepository doctorRepository;
    private final PacientRepository pacientRepository;
    private final List<ValidadorAgendamentoDeConsulta> validadores;

    private boolean active;

    public AgendaDeConsultas(ConsultaRepository consultaRepository,
                             DoctorRepository doctorRepository,
                             PacientRepository pacientRepository,
                             List<ValidadorAgendamentoDeConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.doctorRepository = doctorRepository;
        this.pacientRepository = pacientRepository;
        this.validadores = validadores;
        this.active = true;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        var pacient = pacientRepository.findById(dados.idPacient())
                .orElseThrow(() -> new EntityNotFoundException("ID do paciente não existe"));

        if (dados.idDoctor() != null && !doctorRepository.existsById(dados.idDoctor())) {
            throw new EntityNotFoundException("ID do médico não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        var doctor = escolherMedico(dados);
        var consulta = new Consulta(null, doctor, pacient, dados.data());
        var consultaSalva = consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consultaSalva);
    }

    private Doctor escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idDoctor() != null) {
            return doctorRepository.findById(dados.idDoctor())
                    .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        var doctor = doctorRepository.escolherRandomDoctorLivreNaData(
                dados.especialidade().name(),
                dados.data()
        );        if (doctor == null) {
            throw new ValidacaoException("Não há médico disponível nessa data para a especialidade informada");
        }

        return doctor;
    }

    public void cancelar(Long id) {
        this.active = false;
    }
}