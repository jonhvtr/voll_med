package med.voll.api.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataScheduleAppointment(
        Long idDoctor,
        @NotNull Long idPatient,
        @NotNull Speciality especialidade,
        @NotNull @Future LocalDateTime date) {
}
