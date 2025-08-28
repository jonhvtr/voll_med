package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Appointment;
import med.voll.api.domain.enums.Speciality;

import java.time.LocalDateTime;

public record DataDetailsAppointment(Long id, Long idDoctor, Long idPatient, Speciality especialidade, LocalDateTime date) {
    public DataDetailsAppointment(Appointment appointment) {
        this(appointment.getId(), appointment.getMedico().getId(), appointment.getPaciente().getId(),
                appointment.getEspecialidade(),
                appointment.getData());
    }
}
