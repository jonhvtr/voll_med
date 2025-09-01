package med.voll.api.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByAtivoTrue(Pageable pageable);

    Page<Patient> findByAtivoFalse(Pageable pageable);

    @Query("""
            select p.ativo
            from Patient p
            where p.id = :idPatient
            """)
    boolean findAtivoById(UUID idPatient);

    boolean existsById(UUID uuid);

    Patient getReferenceById(@NotNull UUID uuid);

    Optional<Patient> findByNomeIgnoreCase(String nome);
}
