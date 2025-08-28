package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataAddress(
        @NotBlank String logradouro,
        @NotBlank String bairro,
        @NotBlank @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido") String cep,
        @NotBlank String cidade,
        @NotBlank String uf,
        String complemento, String numero) {
}
