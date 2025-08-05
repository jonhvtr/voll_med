package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataCancelAppointment;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CancelScheduleValidator implements CancelAppointmentValidator {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(DataCancelAppointment data) {
        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getData()).toHours();

        if (differenceInHours < 24) {
            throw new VollException("Consulta somente pode ser cancelada com 24 horas de antecedência");
        }
    }
}
