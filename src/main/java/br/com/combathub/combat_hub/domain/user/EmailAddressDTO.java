package br.com.combathub.combat_hub.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailAddressDTO(
        @NotNull
        @Email
        String email
) {
}
