package br.com.combathub.combat_hub.domain.verification_code;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long> {

    VerificationCodeEntity findByCode(String code);

}
