package com.med.doctorss.entity.doctor;

import com.med.doctorss.entity.address.Address;
import com.med.doctorss.entity.address.DataAddress;
import com.med.doctorss.entity.consulta.Consulta;
import com.med.doctorss.entity.pacient.DataRegisterPacient;
import com.med.doctorss.entity.pacient.Pacient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve devolver null quando único doctor cadastrado não estar disponível na data")
    void escolherRandomDoctorLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate().atTime(10, 0);

        var doctor =
                var pacient;
        var consulta;
        var doctorLivre = doctorRepository.escolherRandomDoctorLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        assertThat(doctorLivre).isNull();
    }

    private Doctor cadastrarDoctor(String nome, String email, String crm, Especialidade especialidade) {
        var doctor = new Doctor(dadosDoctor(nome, email, crm, especialidade));
        em.persist(doctor);
        return doctor;
    }

    private DataRegisterDoc dadosDoctor(String nome, String email, String crm, Especialidade especialidade) {
        return new DataRegisterDoc(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private Pacient cadastrarPacient(String nome, String email, String cpf) {
        var pacient = new Pacient(dadosPacient(nome, email, cpf));
        em.persist(pacient);
        return pacient;
    }

    private DataRegisterPacient dadosPacient(String nome, String email, String cpf) {
        return new DataRegisterPacient(
                nome,
                email,
                "61988888888"
        );
    }

    private Consulta cadastrarConsulta(Doctor doctor, Pacient pacient, LocalDateTime data) {
        var consulta = new Consulta(null, doctor, pacient, data);
        em.persist(consulta);
        return consulta;
    }

    private DataAddress dadosEndereco() {
        return new DataAddress(
                "Rua das Flores",
                "Centro",
                "12345678",
                "Brasília",
                "DF",
                null,
                null
        );
    }
}