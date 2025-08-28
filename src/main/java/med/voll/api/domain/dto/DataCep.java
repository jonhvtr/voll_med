package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataCep(
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido") String cep,
        String logradouro,
        String bairro,
        String complemento,
        @JsonAlias("estado") String cidade,
        String uf) {
}
