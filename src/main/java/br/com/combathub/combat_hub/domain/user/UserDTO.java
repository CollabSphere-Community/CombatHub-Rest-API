package br.com.combathub.combat_hub.domain.user;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserDTO(
        @NotNull
        @Email
        String login,

        @NotNull
        @Length(min = 8)
        String password,

        @Enumerated
        UserRole role
) {
}
