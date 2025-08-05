package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicOperatingHoursValidator implements AppointmentSchedulerValidator {
    public void validate(DataScheduleAppointment data) {
        var appointmentDate = data.date();
        var isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var isBeforeClinicOpens = appointmentDate.getHour() < 7;
        var isAfterClinicCloses = appointmentDate.getHour() > 18;

        if (isSunday || isBeforeClinicOpens || isAfterClinicCloses) {
            throw new VollException("Consulta fora do horário do funcionamento da clínica");
        }
    }

}
