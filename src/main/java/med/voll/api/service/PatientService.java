package med.voll.api.service;

import med.voll.api.domain.Patient;
import med.voll.api.domain.dto.DataDetailsPatient;
import med.voll.api.domain.dto.DataListPatient;
import med.voll.api.domain.dto.DataPatient;
import med.voll.api.domain.dto.DataUpdatePatient;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public DataDetailsPatient create(DataPatient data) {
        var patient = new Patient(data);
        patientRepository.save(patient);
        return new DataDetailsPatient(patient);
    }

    public Page<DataListPatient> findAll(Pageable pageable) {
        return patientRepository.findByAtivoTrue(pageable).map(DataListPatient::new);
    }

    public DataDetailsPatient findPatient(Long id) {
        var patient = patientRepository.getReferenceById(id);
        return new DataDetailsPatient(patient);
    }

    public DataDetailsPatient update(DataUpdatePatient data) {
        var patient = patientRepository.getReferenceById(data.id());
        patient.updateData(data);
        return new DataDetailsPatient(patient);
    }

    public void delete(Long id) {
        var patient = patientRepository.getReferenceById(id);
        patient.delete();
    }
}
