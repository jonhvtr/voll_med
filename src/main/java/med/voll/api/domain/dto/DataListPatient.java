package med.voll.api.domain.dto;

import med.voll.api.domain.Patient;

public record DadosListPaciente(Long id, String nome, String email, String cpf) {
    public DadosListPaciente(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
