package com.med.doctorss.entity.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacientIdAndDataBetween(Long idPacient, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByDoctorIdAndData(Long idDoctor, LocalDateTime data);
}
