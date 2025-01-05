package br.com.combathub.combat_hub.controller;

import br.com.combathub.combat_hub.domain.user.AuthenticationDTO;
import br.com.combathub.combat_hub.domain.user.UserDTO;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.domain.user.UserService;
import br.com.combathub.combat_hub.domain.verification_code.VerificationCodeService;
import br.com.combathub.combat_hub.infra.security.JWTTokenDTO;
import br.com.combathub.combat_hub.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    //TODO Encapsulate the usage of others services to the UserService
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @PreAuthorize("@userService.isEmailConfirmed(#dto.login())")
    public ResponseEntity<JWTTokenDTO> login(@Valid @RequestBody AuthenticationDTO dto) throws AuthenticationException {
        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        this.authenticationManager.authenticate(token);
        var jwtToken = tokenService.generateToken(token.getName());
        return ResponseEntity.ok(new JWTTokenDTO(jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    @PreAuthorize("@userService.isEmailRegistered(#dto.login())")
    public void register(@Valid @RequestBody UserDTO dto) {
        var encodedPassword = encoder.encode(dto.password());
        var user = this.userService.registerUser(
                new UserEntity(dto.login(), encodedPassword, dto.role())
        );
        this.verificationCodeService.registerCode(user);
    }

}
