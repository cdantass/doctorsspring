package com.med.doctorss.controller;

import com.med.doctorss.entity.consulta.AgendaDeConsultas;
import com.med.doctorss.entity.consulta.DadosAgendamentoConsulta;
import com.med.doctorss.entity.consulta.DadosDetalhamentoConsulta;
import com.med.doctorss.entity.doctor.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultajson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultajson;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400, quando as informações forem inválidas")
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200, quando as informações forem válidas")
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        when(agendaDeConsultas.agendar(any())).thenReturn(new DadosDetalhamentoConsulta(null, 2l, 5l, data));

        var dadosAgendamento = new DadosAgendamentoConsulta(2L, 5L, data, especialidade);
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5L, data);

        given(agendaDeConsultas.agendar(any())).willReturn(dadosDetalhamento);

        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultajson.write(dadosAgendamento).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultajson.write(
                new DadosDetalhamentoConsulta(null, 2l, 5l, data)
        ).getJson();

        assertThat(response
                .getContentAsString())
                .isEqualTo(jsonEsperado);
    }
}