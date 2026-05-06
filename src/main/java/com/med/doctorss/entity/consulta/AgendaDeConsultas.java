package com.med.doctorss.entity.consulta;

import com.med.doctorss.entity.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.med.doctorss.entity.doctor.Doctor;
import com.med.doctorss.entity.doctor.DoctorRepository;
import com.med.doctorss.entity.pacient.PacientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRepository consultaRepository;
    private final DoctorRepository doctorRepository;
    private final PacientRepository pacientRepository;
    private final List<ValidadorAgendamentoDeConsulta> validadores;

    public AgendaDeConsultas(ConsultaRepository consultaRepository,
                             DoctorRepository doctorRepository,
                             PacientRepository pacientRepository,
                             List<ValidadorAgendamentoDeConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.doctorRepository = doctorRepository;
        this.pacientRepository = pacientRepository;
        this.validadores = validadores;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacientRepository.existsById(dados.idPacient())) {
            throw new RuntimeException("ID do paciente não existe");
        }
        if (dados.idDoctor() != null && !doctorRepository.existsById(dados.idDoctor())) {
            throw new RuntimeException("ID do doctor não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        var pacient = pacientRepository.findById(dados.idPacient())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        var doctor = escolherMedico(dados);
        var consulta = new Consulta(null, doctor, pacient, dados.data());
        var consultaSalva = consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consultaSalva);
    }

    private Doctor escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idDoctor() != null) {
            return doctorRepository.findById(dados.idDoctor())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        }
        if (dados.especialidade() == null) {
            throw new RuntimeException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        return doctorRepository.escolherRandomDoctorLivreNaData(dados.especialidade(), dados.data());
    }
}