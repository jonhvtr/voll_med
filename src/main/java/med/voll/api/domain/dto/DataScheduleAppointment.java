package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enums.Speciality;

import java.time.LocalDateTime;
import java.util.UUID;

public record DataScheduleAppointment(
        UUID idDoctor,
        @NotNull UUID idPatient,
        @NotNull Speciality especialidade,
        @NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date) {
}
