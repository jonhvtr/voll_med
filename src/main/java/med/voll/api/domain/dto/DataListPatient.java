package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Patient;

import java.util.UUID;

public record DataListPatient(UUID id, String nome, String email, String cpf) {
    public DataListPatient(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
