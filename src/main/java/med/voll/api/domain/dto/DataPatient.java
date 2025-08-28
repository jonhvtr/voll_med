package med.voll.api.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record DataPatient(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}-?\\d{4})$", message = "Telefone inválido") String telefone,
        @NotBlank @CPF(message = "CPF inválido") String cpf,
        @NotNull @Valid String cep) {
}
