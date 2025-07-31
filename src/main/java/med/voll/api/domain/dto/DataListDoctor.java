package med.voll.api.domain.dto;

import med.voll.api.domain.Doctor;

public record DataListDoctor(Long id, String nome, String email, String crm, Specialty especialidade) {
    public DataListDoctor(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
