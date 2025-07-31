package med.voll.api.domain.dto;

import med.voll.api.domain.Doctor;

public record DadosListMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListMedico(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
