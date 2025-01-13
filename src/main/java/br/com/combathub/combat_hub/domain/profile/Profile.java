package br.com.combathub.combat_hub.domain.profile;

import br.com.combathub.combat_hub.domain.user.UserRegistrationDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@MappedSuperclass
@SuperBuilder
public abstract class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    private String name;

    private String avatar;

    private LocalDateTime registeredAt;

    public Profile(UserRegistrationDTO dto, UserEntity entity) {
        this.user = entity;
        this.name = dto.name();
        this.avatar = dto.avatar();
        this.registeredAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
