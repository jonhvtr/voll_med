package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record DataCancelAppointment(
        @NotNull Long idAppointment,
        @NotNull ReasonCancellation reason
        ) {
}
