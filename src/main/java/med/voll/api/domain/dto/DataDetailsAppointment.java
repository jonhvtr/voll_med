package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import med.voll.api.domain.entities.Appointment;
import med.voll.api.domain.enums.Speciality;

import java.time.LocalDateTime;
import java.util.UUID;

public record DataDetailsAppointment(UUID id, String doctor, String patient, Speciality especialidade,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm") LocalDateTime date) {
    public DataDetailsAppointment(Appointment appointment) {
        this(appointment.getId(), appointment.getMedico().getNome(), appointment.getPaciente().getNome(),
                appointment.getEspecialidade(),
                appointment.getData());
    }
}
