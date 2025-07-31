package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record DataUpdateDoctor(
        @NotNull Long id, String nome, String telefone, DataAddress endereco) {
}
