package med.voll.api.domain;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DataDoctor;
import med.voll.api.domain.dto.DataUpdateDoctor;
import med.voll.api.domain.dto.Speciality;

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
        this.crm = data.crm();
        this.telefone = data.telefone();
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
}
