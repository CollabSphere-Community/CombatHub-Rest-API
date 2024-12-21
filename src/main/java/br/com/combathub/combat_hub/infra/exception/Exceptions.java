package br.com.combathub.combat_hub.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity emailAlreadyRegistered() {
        return ResponseEntity.badRequest().body("email_already_registered");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldValidationDTO>> validationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                ex.getFieldErrors()
                        .stream()
                        .map(FieldValidationDTO::new)
                        .toList()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationException() {
        return ResponseEntity.status(403).body("authentication_failed");
    }

    @ExceptionHandler(EmailNotConfirmedException.class)
    public ResponseEntity emailNotConfirmed() {
        return ResponseEntity.badRequest().body("email_not_confirmed");
    }

    @ExceptionHandler(EmailNotRegisteredException.class)
    public ResponseEntity emailNotRegistered() {
        return ResponseEntity.notFound().build();
    }

}
