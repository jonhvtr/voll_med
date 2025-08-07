package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DataCancelAppointment;
import med.voll.api.domain.dto.DataScheduleAppointment;
import med.voll.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> schedule(@RequestBody @Valid DataScheduleAppointment data) {
        var dto = appointmentService.schedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> cancel(@RequestBody @Valid DataCancelAppointment data) {
        appointmentService.cancelAppointment(data);
        return ResponseEntity.noContent().build();
    }
}
