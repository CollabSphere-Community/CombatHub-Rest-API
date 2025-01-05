package br.com.combathub.combat_hub.domain.verification_code;

import br.com.combathub.combat_hub.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name="user_verification_code")
@Getter
@NoArgsConstructor
public class VerificationCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    private LocalDateTime expiresAt;

    @Setter
    private boolean valid;

    public VerificationCodeEntity(UserEntity user) {
        this.code = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt =
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2);
        this.valid = true;
    }

}
