package med.voll.api.controller;

import med.voll.api.domain.dto.DataDetailsAppointment;
import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.domain.enums.Speciality;
import med.voll.api.service.AppointmentService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataScheduleAppointment> dataScheduleAppointmentJacksonTester;

    @Autowired
    private JacksonTester<DataDetailsAppointment> dataDetailsAppointmentJacksonTester;

    @MockitoBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando as informacoes estao invalidas")
    @WithMockUser
    void scheduleScenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando as informacoes estao validas")
    @WithMockUser
    void scheduleScenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var speciality = Speciality.CARDIOLOGIA;
        var dataDetails = new DataDetailsAppointment(null, 2l, 5l, speciality, date);

        when(appointmentService.schedule(any())).thenReturn(dataDetails);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataScheduleAppointmentJacksonTester.write(
                                new DataScheduleAppointment(2l, 5l, speciality, date)
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = dataDetailsAppointmentJacksonTester.write(dataDetails).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

}