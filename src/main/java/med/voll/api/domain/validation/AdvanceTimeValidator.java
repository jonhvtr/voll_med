package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceTimeValidator implements AppointmentSchedulerValidator {
    public void validate(DataScheduleAppointment data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new VollException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
