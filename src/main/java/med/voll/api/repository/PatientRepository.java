package med.voll.api.repository;

import med.voll.api.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByAtivoTrue(Pageable pageable);

    @Query("""
            select p.ativo
            from Patient p
            where p.id = :idPatient
            """)
    boolean findAtivoById(Long idPatient);
}
