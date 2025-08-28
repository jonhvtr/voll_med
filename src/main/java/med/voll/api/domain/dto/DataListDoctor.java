package med.voll.api.domain.dto;

import med.voll.api.domain.entities.Doctor;
import med.voll.api.domain.enums.Speciality;

public record DataListDoctor(Long id, String nome, String email, String crm, Speciality especialidade) {
    public DataListDoctor(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
