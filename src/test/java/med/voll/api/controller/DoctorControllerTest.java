package med.voll.api.controller;

import med.voll.api.domain.Address;
import med.voll.api.domain.Doctor;
import med.voll.api.domain.dto.*;
import med.voll.api.repository.DoctorRepository;
import med.voll.api.service.CepService;
import med.voll.api.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
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
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataDoctor> dataDoctorJacksonTester;

    @Autowired
    private JacksonTester<DataDetailsDoctor> dataDetailsDoctorJacksonTester;

    @Autowired
    private JacksonTester<Page<DataListDoctor>> dataListDoctorJacksonTester;

    @MockitoBean
    private DoctorRepository doctorRepository;

    @MockitoBean
    private CepService cepService;

    @Autowired
    private DoctorService doctorService;

    @BeforeEach
    void setup() {
        doctorRepository.deleteAll();
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
    void createDoctorScenario1() throws Exception {
        var speciality = Speciality.CARDIOLOGIA;
        var address = new Address("ruaXTPO", "Bairro Central", "000000000", null, null, "Brasilia", "DF");
        var dataDetails = new DataDetailsDoctor(null, "Joao", "joao@voll.med", "321456", "21999999999", speciality, address);

        var response = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataDoctorJacksonTester.write(
                                new DataDoctor("Joao", "joao@voll.med", "21999999999", "321456", speciality, "000000000")
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = dataDetailsDoctorJacksonTester.write(dataDetails).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 e listar medicos")
    @WithMockUser
    void listAllScenario1() throws Exception {
        var pageable = PageRequest.of(0, 10, Sort.by("nome"));
        var doctor = new Doctor(
                1L,
                "Joao",
                "joao@voll.med",
                "21999999999",
                "321456",
                Speciality.CARDIOLOGIA,
                new Address("ruaXTPO", "Bairro Central", "000000000", null, null, "Brasilia", "DF"),
                true
        );

        var page = new PageImpl<>(List.of(doctor), pageable, 1);
        when(doctorRepository.findAllByAtivoTrue(pageable)).thenReturn(page);

        var response = mockMvc.perform(get("/medicos")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "nome")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString()).contains("Joao", "joao@voll.med", "321456", "CARDIOLOGIA");
    }
}