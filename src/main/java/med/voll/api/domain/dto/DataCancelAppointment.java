package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enums.ReasonCancellation;

import java.util.UUID;

public record DataCancelAppointment(
        @NotNull UUID idAppointment,
        @NotNull ReasonCancellation reason
) {
}
