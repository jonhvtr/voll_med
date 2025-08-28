package med.voll.api.repository;

import med.voll.api.domain.entities.Doctor;
import med.voll.api.domain.enums.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            select d from Doctor d
            where
            d.ativo = true
            and
            d.especialidade = :speciality
            and
            d.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :date
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctor(Speciality speciality, LocalDateTime date);

    @Query("""
            select d.ativo
            from Doctor d
            where d.id = :idDoctor
            """)
    Boolean findAtivoById(Long idDoctor);
}
