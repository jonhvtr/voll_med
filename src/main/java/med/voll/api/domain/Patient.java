package med.voll.api.domain;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.dto.DataPatient;
import med.voll.api.domain.dto.DataUpdatePatient;

@Entity(name = "Patient")
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        this.telefone = data.telefone();
        this.cpf = data.cpf();
        this.endereco = endereco;
        this.ativo = true;
    }

    public void updateData(DataUpdatePatient data) {
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
