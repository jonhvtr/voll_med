package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;

public interface AppointmentSchedulerValidator {
    void validate(DataScheduleAppointment data);
}
