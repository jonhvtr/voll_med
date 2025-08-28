package med.voll.api.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.DataAddress;
import med.voll.api.domain.dto.DataCep;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Address(DataCep data) {
        this.logradouro = data.logradouro();
        this.bairro = data.bairro();
        setCep(data.cep());
        this.complemento = data.complemento();
        this.cidade = data.cidade();
        this.uf = data.uf();
    }

    public void updateInformation(DataAddress data) {
        if (data.logradouro() != null) {
            this.logradouro = data.logradouro();
        }
        if (data.bairro() != null) {
            this.bairro = data.bairro();
        }
        if (data.cep() != null) {
            this.cep = data.cep();
        }
        if (data.numero() != null) {
            this.numero = data.numero();
        }
        if (data.complemento() != null) {
            this.complemento = data.complemento();
        }
        if (data.cidade() != null) {
            this.cidade = data.cidade();
        }
        if (data.uf() != null) {
            this.uf = data.uf();
        }
    }

    public void setCep(String cep) {
        this.cep = cep.replaceAll("\\D", "");
    }

    public String getCep() {
        return cep.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
    }
}
