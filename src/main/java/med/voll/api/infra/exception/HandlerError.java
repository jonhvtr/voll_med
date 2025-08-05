package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.rmi.AccessException;

@RestControllerAdvice
public class HandlerError {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerErrorNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerErroBadRequest(MethodArgumentNotValidException exception) {
        var error = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(DataValidationError::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handlerErroBadRequest(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handlerErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handlerError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha de autenticação");
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<?> handlerErrorAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerErrorServerError(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + exception.getLocalizedMessage());
    }

    @ExceptionHandler(VollException.class)
    public ResponseEntity<?> handlerErrorBusinessRoles(VollException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


    private record DataValidationError(String field, String message) {
        DataValidationError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
