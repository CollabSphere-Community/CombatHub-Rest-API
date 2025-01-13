package br.com.combathub.combat_hub.domain.profile;

import br.com.combathub.combat_hub.domain.user.UserRegistrationDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.domain.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public void createProfile(UserRegistrationDTO dto, UserEntity user) {
        if(dto.role().equals(UserRole.ATHLETE)) {
            athleteRepository.save(new AthleteEntity(dto, user));
        } else if (dto.role().equals(UserRole.GYM)) {
            gymRepository.save(new GymEntity(dto, user));
        } else {
            organizationRepository.save(new OrganizationEntity(dto, user));
        };
    }

}
