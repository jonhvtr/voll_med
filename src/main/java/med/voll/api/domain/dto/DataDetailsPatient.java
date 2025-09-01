package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Address;
import med.voll.api.domain.entities.Patient;

import java.util.UUID;

public record DataDetailsPatient(UUID id, String nome, String email,
                                 String telefone,
                                 String cpf,
                                 Address endereco) {

    public DataDetailsPatient(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getTelefone(),
                patient.getCpf(),
                patient.getEndereco());
    }
}
