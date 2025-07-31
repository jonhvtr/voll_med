package med.voll.api.repository;

import med.voll.api.domain.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByAtivoTrue(Pageable pageable);
}
