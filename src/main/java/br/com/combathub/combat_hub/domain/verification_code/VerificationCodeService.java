package br.com.combathub.combat_hub.domain.verification_code;

import br.com.combathub.combat_hub.domain.email_sender.EmailService;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private VerificationCodeRepository repository;

    @Autowired
    private EmailService emailService;

    public void registerCode(UserEntity user) {
        var entity = repository.save(new VerificationCodeEntity(user));
        emailService.sendVerificationCode(entity);
    }

    public void setExistingCodesAsInvalid(UserEntity user) {
        var codes = repository.findAllByUserAndValidTrue(user);
        codes.forEach(code -> code.setValid(false));
    }

}
