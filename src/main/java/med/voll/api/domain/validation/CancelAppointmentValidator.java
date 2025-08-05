package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataCancelAppointment;

public interface CancelAppointmentValidator {
    void validate(DataCancelAppointment data);
}
