package med.voll.api.dto;

import med.voll.api.domain.Paciente;

public record DadosListPaciente(Long id, String nome, String email, String cpf) {
    public DadosListPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
