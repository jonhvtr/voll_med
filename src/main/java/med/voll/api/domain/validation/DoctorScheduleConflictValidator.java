package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorScheduleConflictValidator implements AppointmentSchedulerValidator {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(DataScheduleAppointment data) {
        var hasAnotherAppointmentAtSameTime = appointmentRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(data.idDoctor(),
                data.date());
        if (hasAnotherAppointmentAtSameTime) {
            throw new VollException("Médico já possui outra consulta agendada nesse mesmo horário");
        }

    }
}
