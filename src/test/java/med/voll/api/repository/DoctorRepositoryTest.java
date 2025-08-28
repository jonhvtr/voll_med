package med.voll.api.repository;

import med.voll.api.domain.entities.Address;
import med.voll.api.domain.entities.Appointment;
import med.voll.api.domain.entities.Doctor;
import med.voll.api.domain.entities.Patient;
import med.voll.api.domain.enums.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void cleanDB() {
        entityManager.getEntityManager().createQuery("DELETE FROM Consulta").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Patient").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Doctor").executeUpdate();
    }

    @Test
    @DisplayName("Deveria devolver null quando medico cadastrado nao esta disponivel na data")
    //given ou arrange
    void chooseRandomDoctorScenario1() {
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var address = new Address("Rua XPTO", "bairro", "000000000", null, null, "Brasilia", "DF");
        var doctor = new Doctor(null, "Joao", "joao@voll.med", "61999999999", "123543", Speciality.CARDIOLOGIA, address,
                true);
        entityManager.persist(doctor);

        var patient = new Patient(null, "Carlos", "carlos@gmail.com", "31987898767", "32145321234", address, true);
        entityManager.persist(patient);

        var appointment = new Appointment(null, doctor, patient, null, nextMondayAt10, null);
        entityManager.persist(appointment);

        entityManager.flush();
        entityManager.clear();

        //when ou act
        var freeDoctor = doctorRepository.chooseRandomDoctor(Speciality.CARDIOLOGIA, nextMondayAt10);
        //then ou assert
        assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void chooseRandomDoctorScenario2() {
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var address = new Address("Rua XPTO", "bairro", "000000000", null, null, "Brasilia", "DF");
        var doctor = new Doctor(null, "Amanda", "amanda@voll.med", "61999999899", "223543", Speciality.CARDIOLOGIA, address,
                true);
        entityManager.persist(doctor);

        entityManager.flush();
        entityManager.clear();

        var freeDoctor = doctorRepository.chooseRandomDoctor(Speciality.CARDIOLOGIA, nextMondayAt10);
        assertThat(freeDoctor.getEmail()).isEqualTo(doctor.getEmail());
    }
}