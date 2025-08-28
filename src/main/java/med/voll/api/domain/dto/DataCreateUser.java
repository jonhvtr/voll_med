package med.voll.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataCreateUser(@NotBlank @Email String login, @NotBlank String senha) {
}
