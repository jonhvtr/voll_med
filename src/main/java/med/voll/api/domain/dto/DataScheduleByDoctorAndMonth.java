package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DataScheduleByDoctorAndMonth(@NotNull UUID idDoctor, @NotNull int year, @NotNull int month) {
}
