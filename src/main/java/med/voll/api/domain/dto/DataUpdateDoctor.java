package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DataUpdateDoctor(
        @NotNull UUID id, String nome, String telefone, DataAddress endereco) {
}
