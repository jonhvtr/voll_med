package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataCep(String cep, String logradouro, String bairro, String complemento,
                      @JsonAlias("estado") String cidade, String uf) {
}
