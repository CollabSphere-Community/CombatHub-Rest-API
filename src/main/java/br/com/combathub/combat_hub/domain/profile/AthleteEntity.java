package br.com.combathub.combat_hub.domain.profile;

import br.com.combathub.combat_hub.domain.MartialArtsModalities;
import br.com.combathub.combat_hub.domain.user.UserRegistrationDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "athletes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class AthleteEntity extends Profile {

    private MartialArtsModalities modality;

    public AthleteEntity(UserRegistrationDTO dto, UserEntity user) {
        super(dto, user);
        this.modality = dto.modality();
    }

}
