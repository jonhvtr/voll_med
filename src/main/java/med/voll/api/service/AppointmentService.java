package med.voll.api.service;

import med.voll.api.domain.Appointment;
import med.voll.api.domain.Doctor;
import med.voll.api.domain.dto.DataCancelAppointment;
import med.voll.api.domain.dto.DataDetailsAppointment;
import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.domain.validation.AppointmentSchedulerValidator;
import med.voll.api.domain.validation.CancelAppointmentValidator;
import med.voll.api.infra.exception.VollException;
import med.voll.api.repository.AppointmentRepository;
import med.voll.api.repository.DoctorRepository;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private final List<AppointmentSchedulerValidator> validators;

    @Autowired
    private final List<CancelAppointmentValidator> cancelValidators;

    public AppointmentService(List<AppointmentSchedulerValidator> validators,
                              List<CancelAppointmentValidator> cancelValidators) {
        this.validators = validators;
        this.cancelValidators = cancelValidators;
    }

    public DataDetailsAppointment schedule(DataScheduleAppointment data) {
        if (!patientRepository.existsById(data.idPatient())) {
            throw new VollException("Id do paciente informado não existe!");
        }

        if (data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor())) {
            throw new VollException("Id do médico informado não existe!");
        }

        validators.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);
        if (doctor == null) {
            throw new VollException("Não existe médico nessa data");
        }
        var appointment = new Appointment(null, doctor, patient, doctor.getEspecialidade(), data.date(), null);
        appointmentRepository.save(appointment);

        return new DataDetailsAppointment(appointment);
    }

    public void cancelAppointment(DataCancelAppointment data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new VollException("Id da consulta informado não existe!");
        }

        cancelValidators.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

    private Doctor chooseDoctor(DataScheduleAppointment data) {
        if (data.idDoctor() != null) {
            return doctorRepository.getReferenceById(data.idDoctor());
        }

        if (data.especialidade() == null) {
            throw new VollException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        return doctorRepository.chooseRandomDoctor(data.especialidade(), data.date());
    }
}
