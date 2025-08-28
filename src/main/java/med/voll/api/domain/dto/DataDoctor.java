package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.enums.Speciality;

public record DataDoctor(
        @NotBlank(message = "{nome.obrigatorio}") String nome,
        @NotBlank(message = "{email.obrigatorio}") @Email(message = "{email.invalido}") String email,
        @NotBlank(message = "{telefone.obrigatorio}")
        @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}-?\\d{4})$", message = "Telefone inválido") String telefone,
        @NotBlank(message = "{crm.obrigatorio}") @Pattern(regexp = "^(\\d{4,6})(-?[A-Z]{2})?$", message = "CRM inválido") String crm,
        @NotNull(message = "{especialidade.obrigatorio}") Speciality especialidade,
        @NotNull(message = "{endereco.obrigatorio}") @Valid String cep) {
}
