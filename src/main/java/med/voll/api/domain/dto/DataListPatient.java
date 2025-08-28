package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Patient;

public record DataListPatient(Long id, String nome, String email, String cpf) {
    public DataListPatient(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
