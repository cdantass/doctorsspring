package com.med.doctorss.entity.doctor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable paginacao);
    boolean existsByIdAndActiveTrue(Long id);

    @Query(value = """
    SELECT * FROM doctor m
    WHERE m.active = true
    AND m.especialidade = :especialidade
    AND m.id NOT IN (
        SELECT c.doctor.id FROM consulta c WHERE c.data = :data
    )
    ORDER BY RANDOM()
    LIMIT 1
""", nativeQuery = true)
    Doctor escolherRandomDoctorLivreNaData(Especialidade especialidade, LocalDateTime data);
}
