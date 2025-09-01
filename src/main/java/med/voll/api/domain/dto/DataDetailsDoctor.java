package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Address;
import med.voll.api.domain.entities.Doctor;
import med.voll.api.domain.enums.Speciality;

import java.util.UUID;

public record DataDetailsDoctor(UUID id, String nome, String email, String crm,
                                String telefone, Speciality especialidade,
                                Address endereco) {

    public DataDetailsDoctor(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(),
                doctor.getEspecialidade(),
                doctor.getEndereco());
    }
}
