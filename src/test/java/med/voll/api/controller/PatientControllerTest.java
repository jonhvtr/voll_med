package med.voll.api.controller;

import med.voll.api.domain.entities.Address;
import med.voll.api.domain.entities.Patient;
import med.voll.api.domain.dto.*;
import med.voll.api.repository.PatientRepository;
import med.voll.api.service.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataPatient> dataPatientJacksonTester;

    @Autowired
    private JacksonTester<DataDetailsPatient> dataDetailsPatientJacksonTester;

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private CepService cepService;

    @BeforeEach
    void setup() {
        when(cepService.searchCep("000000000")).thenReturn(
                new DataCep(
                        "000000000",
                        "ruaXTPO",
                        "Bairro Central",
                        null,
                        "Brasilia",
                        "DF"
                ));
    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando as informacoes estao validas")
    @WithMockUser
    void createPatient() throws Exception {
        var address = new Address("ruaXTPO", "Bairro Central", "000000000", null, null, "Brasilia", "DF");
        var dataDetails = new DataDetailsPatient(null, "Joao", "joao@email.com", "21999999999", "99999999999", address);

        var response = mockMvc.perform(post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataPatientJacksonTester.write(
                                new DataPatient("Joao", "joao@email.com", "21999999999", "99999999999", "000000000")
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var ExpectedJson = dataDetailsPatientJacksonTester.write(dataDetails).getJson();
        assertThat(response.getContentAsString()).isEqualTo(ExpectedJson);
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 e listar pacientes")
    @WithMockUser
    void listAllScenario1() throws Exception {
        var pageable = PageRequest.of(0, 10, Sort.by("nome"));
        var patient = new Patient(
                1L,
                "Joao",
                "joao@email.com",
                "21999999999",
                "999999999",
                new Address("ruaXTPO", "Bairro Central", "000000000", null, null, "Brasilia", "DF"),
                true
        );

        var page = new PageImpl<>(List.of(patient), pageable, 1);
        when(patientRepository.findByAtivoTrue(pageable)).thenReturn(page);

        var response = mockMvc.perform(get("/paciente")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "nome")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Joao", "joao@email.com", "999999999");
    }
}