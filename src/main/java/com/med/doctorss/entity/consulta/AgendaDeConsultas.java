package com.med.doctorss.entity.consulta;

import com.med.doctorss.entity.doctor.Doctor;
import com.med.doctorss.entity.doctor.DoctorRepository;
import com.med.doctorss.entity.pacient.Pacient;
import com.med.doctorss.entity.pacient.PacientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {


    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PacientRepository pacientRepository;

    public void agendar(DadosAgendamentoConsulta dados){
        if (!pacientRepository.existsById(dados.idPacient())){
            throw new RuntimeException("ID do paciente não existe");
        }
        if (dados.idDoctor() !=null && !doctorRepository.existsById(dados.idDoctor())){
            throw new RuntimeException("ID do doctor não existe");
        }
        var pacient = pacientRepository.getReferenceById(dados.idPacient());
        var doctor = escolherMedico(dados);
        var consulta = new Consulta(null, doctor, pacient, dados.data());
        consultaRepository.save(consulta);
    }

    private Doctor escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idDoctor() != null){
            return doctorRepository.getReferenceById(dados.idDoctor());
        }
        if (dados.especialidade() == null){
            throw new RuntimeException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        return doctorRepository.escolherRandomDoctorLivreNaData(dados.especialidade(), dados.data());
    }
}