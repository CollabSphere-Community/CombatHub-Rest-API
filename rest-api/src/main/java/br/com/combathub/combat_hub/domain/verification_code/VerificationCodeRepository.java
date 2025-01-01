package br.com.combathub.combat_hub.domain.verification_code;

import br.com.combathub.combat_hub.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long> {

    VerificationCodeEntity findByCode(String code);

    List<VerificationCodeEntity> findAllByUserAndValidTrue(UserEntity user);

}
