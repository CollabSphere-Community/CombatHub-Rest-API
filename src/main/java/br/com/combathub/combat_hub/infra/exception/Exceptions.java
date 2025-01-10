package br.com.combathub.combat_hub.infra.exception;

import br.com.combathub.combat_hub.infra.response.ResponseHandler;
import br.com.combathub.combat_hub.infra.security.VerificationCodeExpiredException;
import br.com.combathub.combat_hub.infra.security.VerificationCodeInvalidException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
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

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity jwtVerification() {
        return ResponseEntity.badRequest().body("invalid_jtw_token");
    }

    @ExceptionHandler(UserAlredyConfirmedException.class)
    public ResponseEntity userAlreadyConfirmed() {
        return ResponseEntity.badRequest().body("user_already_confirmed");
    }

    @ExceptionHandler(VerificationCodeExpiredException.class)
    public ResponseEntity codeExpired() {
        return ResponseEntity.badRequest().body("verification_code_is_expired");
    }

    @ExceptionHandler(VerificationCodeInvalidException.class)
    public ResponseEntity codeInvalid() {
        return ResponseEntity.badRequest().body("verification_code_is_not_valid");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFound() {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(EventNotFoundExpection.class)
    public ResponseEntity<Object> eventNotFound(EventNotFoundExpection ex) {
    	EventExpection eventException = new EventExpection(ex.getMessage(), 
    			ex.getCause(),ApplicationErrorCode.ENITY_NOT_FOUND);
    	return new ResponseEntity<Object>(eventException,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DateNotValidException.class)
    public ResponseEntity<Object> dateInvalid(DateNotValidException ex) {
    	EventExpection eventException = new EventExpection(ex.getMessage(), 
    			ex.getCause(),ApplicationErrorCode.DATE_NOT_VALUE);
    	return ResponseHandler.errorResponseBuilder(HttpStatus.BAD_REQUEST, eventException);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
        	EventExpection eventException = new EventExpection("Invalid date-time format. Please use 'yyyy-MM-ddTHH:mm:ss'.", 
        			ex.getCause(),ApplicationErrorCode.INVALID_DATE_FORMAT);
            return ResponseHandler.errorResponseBuilder(HttpStatus.BAD_REQUEST,eventException);
        }
        
        EventExpection eventException = new EventExpection("Invalid Request Body", 
    			ex.getCause(),ApplicationErrorCode.INVALID_REQUEST_BODY);
        return ResponseHandler.errorResponseBuilder(HttpStatus.BAD_REQUEST,eventException);
    }
    
}
