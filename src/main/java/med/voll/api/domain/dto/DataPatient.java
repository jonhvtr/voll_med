package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosPaciente(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\d{11}") String telefone,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull @Valid DadosEndereco endereco) {
}
