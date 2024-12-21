package br.com.combathub.combat_hub.infra.exception;

import org.springframework.validation.FieldError;

public record FieldValidationDTO(
        String field,
        String message
) {
    public FieldValidationDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
