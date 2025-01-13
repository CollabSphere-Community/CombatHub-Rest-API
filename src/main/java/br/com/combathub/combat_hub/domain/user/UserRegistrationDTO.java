package br.com.combathub.combat_hub.domain.user;

import br.com.combathub.combat_hub.domain.MartialArtsModalities;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRegistrationDTO(
        @NotNull
        @Email
        String login,

        @NotNull
        @Length(min = 8)
        String password,

        @NotNull
        @Enumerated
        UserRole role,

        @NotNull
        @Length(min = 3, max = 100)
        String name,

        @Enumerated
        MartialArtsModalities modality,

        String avatar
) {
}
