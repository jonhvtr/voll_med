package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record DadosUpdateMedico(
        @NotNull Long id, String nome, String telefone, DadosEndereco endereco) {
}
