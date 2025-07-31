package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataDoctor(
        @NotBlank(message = "{nome.obrigatorio}") String nome,
        @NotBlank(message = "{email.obrigatorio}") @Email(message = "{email.invalido}") String email,
        @NotBlank(message = "{telefone.obrigatorio}") @Pattern(regexp = "\\d{11}") String telefone,
        @NotBlank(message = "{crm.obrigatorio}") @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}") String crm,
        @NotNull(message = "{especialidade.obrigatorio}") Specialty especialidade,
        @NotNull(message = "{endereco.obrigatorio}") @Valid DataAddress endereco) {
}
