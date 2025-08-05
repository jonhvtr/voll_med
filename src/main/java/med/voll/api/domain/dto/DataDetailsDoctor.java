package med.voll.api.domain.dto;

import med.voll.api.domain.Address;
import med.voll.api.domain.Doctor;

public record DataDetailsDoctor(Long id, String nome, String email, String crm,
                                String telefone, Speciality especialidade,
                                Address endereco) {

    public DataDetailsDoctor(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(),
                doctor.getEspecialidade(),
                doctor.getEndereco());
    }
}
