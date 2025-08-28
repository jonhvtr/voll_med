package med.voll.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DataDoctor;
import med.voll.api.domain.dto.DataUpdateDoctor;
import med.voll.api.domain.enums.Speciality;

@Entity(name = "Doctor")
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Speciality especialidade;

    @Embedded
    private Address endereco;

    private Boolean ativo;

    public Doctor(DataDoctor data, Address endereco) {
        this.nome = data.nome();
        this.email = data.email();
        setCrm(data.crm());
        setTelefone(data.telefone());
        this.especialidade = data.especialidade();
        this.ativo = true;
        this.endereco = endereco;
    }

    public void updateInformation(DataUpdateDoctor data) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }
        if (data.telefone() != null) {
            this.telefone = data.telefone();
        }
        if (data.endereco() != null) {
            this.endereco.updateInformation(data.endereco());
        }
    }

    public void delete() {
        this.ativo = false;
    }

    public String getTelefone() {
        return telefone = telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone.replaceAll("\\D", "");
    }

    public String getCrm() {
        return crm.matches("\\d{4,6}[A-Z]{2}")
                ? crm.replaceFirst("(\\d{4,6})([A-Z]{2})", "CRM $1/$2")
                : "CRM " + crm;
    }

    public void setCrm(String crm) {
        this.crm = crm.replaceAll("\\s", "");
    }

}
