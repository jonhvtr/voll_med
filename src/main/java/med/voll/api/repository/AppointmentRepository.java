package med.voll.api.repository;

import med.voll.api.domain.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPacienteIdAndDataBetween(Long idPatient, LocalDateTime firstTime, LocalDateTime lastTime);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idDoctor, LocalDateTime dateTime);
}
