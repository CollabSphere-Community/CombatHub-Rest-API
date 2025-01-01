package br.com.combathub.combat_hub.domain.verification_code;

import br.com.combathub.combat_hub.domain.email_sender.EmailService;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.infra.security.VerificationCodeExpiredException;
import br.com.combathub.combat_hub.infra.security.VerificationCodeInvalidException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public VerificationCodeEntity getCode(String code) {
        var codeEntity = repository.findByCode(code);

        if(codeEntity == null) {
            throw new EntityNotFoundException();
        }

        return codeEntity;
    }

    public boolean isCodeValid(String code) throws VerificationCodeInvalidException, VerificationCodeExpiredException {
        var codeEntity = getCode(code);
        var isCodeExpired = codeEntity
                .getExpiresAt()
                .isBefore(LocalDateTime.now(ZoneId.of("-03:00")));

        if(!codeEntity.isValid()) {
            throw new VerificationCodeInvalidException();
        } else if(isCodeExpired) {
            throw new VerificationCodeExpiredException();
        }

        return true;
    }

}
