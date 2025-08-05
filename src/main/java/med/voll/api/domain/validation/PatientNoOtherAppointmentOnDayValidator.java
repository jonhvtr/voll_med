package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientNoOtherAppointmentOnDayValidator implements AppointmentSchedulerValidator {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(DataScheduleAppointment data) {
        var firstTime = data.date().withHour(7);
        var lastTime = data.date().withHour(18);
        var patientNoOtherAppointmentOnDay = appointmentRepository.existsByPacienteIdAndDataBetween(data.idPatient(),
                firstTime, lastTime);
        if (patientNoOtherAppointmentOnDay) {
            throw new VollException("Paciente já possui consulta agendada nesse dia");
        }
    }

}
