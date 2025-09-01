package med.voll.api.domain.dto;

import java.util.UUID;

public record DataUpdatePatient(UUID id, String nome, String telefone, DataAddress endereco) {
}
