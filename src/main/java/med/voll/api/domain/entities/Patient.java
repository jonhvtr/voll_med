package med.voll.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DataPatient;
import med.voll.api.domain.dto.DataUpdatePatient;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "Patient")
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Address endereco;

    private boolean ativo;

    public Patient(DataPatient data, Address endereco) {
        this.nome = data.nome();
        this.email = data.email();
        setTelefone(data.telefone());
        setCpf(data.cpf());
        this.endereco = endereco;
        this.ativo = true;
    }

    public void updateData(DataUpdatePatient data) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }
        if (data.telefone() != null) {
            setTelefone(data.telefone());
        }
        if (data.endereco() != null) {
            this.endereco.updateInformation(data.endereco());
        }
    }

    public void delete() {
        this.ativo = false;
    }

    public void reativar() {this.ativo = true;}

    public void setCpf(String cpf) {
        this.cpf = cpf.replaceAll("\\D", "");
    }

    public String getCpf() {
        return this.cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4");
    }

    public String getTelefone() {
        return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone.replaceAll("\\D", "");
    }
}
