package med.voll.api.service;

import med.voll.api.domain.Address;
import med.voll.api.domain.Patient;
import med.voll.api.domain.dto.*;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final CepService cepService;

    public PatientService(PatientRepository patientRepository, CepService cepService) {
        this.patientRepository = patientRepository;
        this.cepService = cepService;
    }

    public DataDetailsPatient create(DataPatient data) {
        DataCep dataCep = cepService.searchCep(data.cep());
        var address = new Address(dataCep);
        var patient = new Patient(data, address);
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
