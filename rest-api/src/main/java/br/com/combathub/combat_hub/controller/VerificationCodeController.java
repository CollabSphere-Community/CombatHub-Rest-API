package br.com.combathub.combat_hub.controller;

import br.com.combathub.combat_hub.domain.user.EmailAddressDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.domain.user.UserService;
import br.com.combathub.combat_hub.domain.verification_code.VerificationCodeService;
import br.com.combathub.combat_hub.infra.exception.UserAlredyConfirmedException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification-code")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService codeService;

    @Autowired
    private UserService userService;

    @PostMapping("/new-code")
    @Transactional
    @PreAuthorize("@userService.isUserNotConfirmed((#emailAddress.email()))")
    public ResponseEntity generateNewCode(@RequestBody @Valid EmailAddressDTO emailAddress) {
        var user = (UserEntity) userService.loadUserByUsername(emailAddress.email());
        codeService.setExistingCodesAsInvalid(user);
        codeService.registerCode(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Transactional
    @PreAuthorize("@verificationCodeService.isCodeValid(#code)")
    public ResponseEntity verifyCode(@RequestParam String code) throws UserAlredyConfirmedException {
        var codeEntity = codeService.getCode(code);
        codeService.setExistingCodesAsInvalid(codeEntity.getUser());
        userService.confirmUser(codeEntity.getUser());
        return ResponseEntity.ok().build();
    }

}
