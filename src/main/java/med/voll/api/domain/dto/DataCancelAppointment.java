package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enums.ReasonCancellation;

public record DataCancelAppointment(
        @NotNull Long idAppointment,
        @NotNull ReasonCancellation reason
        ) {
}
