package med.voll.api.service;

import med.voll.api.domain.entities.Address;
import med.voll.api.domain.entities.Doctor;
import med.voll.api.domain.dto.*;
import med.voll.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DoctorService {
    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final CepService cepService;

    public DoctorService(DoctorRepository doctorRepository, CepService cepService) {
        this.doctorRepository = doctorRepository;
        this.cepService = cepService;
    }

    public DataDetailsDoctor create(DataDoctor data) {
        DataCep dataCep = cepService.searchCep(data.cep());
        var address = new Address(dataCep);
        var doctor = new Doctor(data, address);
        doctorRepository.save(doctor);

        return new DataDetailsDoctor(doctor);
    }

    public Page<DataListDoctor> findAll(Pageable pageable) {
        return doctorRepository.findAllByAtivoTrue(pageable).map(DataListDoctor::new);
    }

    public DataDetailsDoctor findDoctor(UUID id) {
        var doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        return new DataDetailsDoctor(doctor);
    }

    public Page<DataListDoctor> findDisableDoctor(Pageable pageable) {
        return doctorRepository.findAllByAtivoFalse(pageable).map(DataListDoctor::new);
    }

    public DataDetailsDoctor update(DataUpdateDoctor data) {
        var doctor = doctorRepository.getReferenceById(data.id());
        doctor.updateInformation(data);

        return new DataDetailsDoctor(doctor);
    }

    public void reactivate(UUID id) {
        var doctor = doctorRepository.getReferenceById(id);
        doctor.reativar();
    }

    public void delete(UUID id) {
        var doctor = doctorRepository.getReferenceById(id);
        doctor.delete();
    }
}
