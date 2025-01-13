package br.com.combathub.combat_hub.domain.profile;

import br.com.combathub.combat_hub.domain.user.UserRegistrationDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrganizationEntity extends Profile {

    public OrganizationEntity(UserRegistrationDTO dto, UserEntity entity) {
        super(dto, entity);
    }

}
