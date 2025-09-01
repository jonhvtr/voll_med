package med.voll.api.repository;

import med.voll.api.domain.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPacienteIdAndDataBetween(UUID idPatient, LocalDateTime firstTime, LocalDateTime lastTime);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(UUID idDoctor, LocalDateTime dateTime);

    boolean existsById(UUID id);

    Appointment getReferenceById(UUID id);

    @Query("""
            select c from Consulta c
            where c.data between :start and :end
            """)
    List<Appointment> findAllByMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
            select c from Consulta c
            where c.medico.id = :idDoctor
            and c.data between :start and :end
            order by c.data
            """)
    List<Appointment> findAllByDoctorByMonth(@Param("idDoctor") UUID idDoctor, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
