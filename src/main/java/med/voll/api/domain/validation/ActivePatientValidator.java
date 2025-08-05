package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientValidator implements AppointmentSchedulerValidator {
    @Autowired
    private PatientRepository patientRepository;

    public void validate(DataScheduleAppointment data) {
        var isActivePatient = patientRepository.findAtivoById(data.idPatient());
        if (!isActivePatient) {
            throw new VollException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
