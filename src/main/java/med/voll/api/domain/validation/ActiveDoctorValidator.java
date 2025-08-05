package med.voll.api.domain.validation;

import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctorValidator implements AppointmentSchedulerValidator {
    @Autowired
    private DoctorRepository doctorRepository;

    public void validate(DataScheduleAppointment data) {
        if (data.idDoctor() == null) {
            return;
        }

        var isActiveDoctor = doctorRepository.findAtivoById(data.idDoctor());
        if (!isActiveDoctor) {
            throw new VollException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
