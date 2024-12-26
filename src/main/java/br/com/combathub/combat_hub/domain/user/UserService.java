package br.com.combathub.combat_hub.domain.user;

import br.com.combathub.combat_hub.infra.exception.EmailAlreadyRegisteredException;
import br.com.combathub.combat_hub.infra.exception.EmailNotConfirmedException;
import br.com.combathub.combat_hub.infra.exception.EmailNotRegisteredException;
import br.com.combathub.combat_hub.infra.exception.UserAlredyConfirmedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    public UserEntity registerUser(UserEntity user) {
        return repository.save(user);
    }

    public boolean isEmailRegistered(String email) throws EmailAlreadyRegisteredException {
        var user = loadUserByUsername(email);
        if(user != null) {
            throw new EmailAlreadyRegisteredException();
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public boolean isEmailConfirmed(String login) throws EmailNotConfirmedException, EmailNotRegisteredException {
        var user = (UserEntity) loadUserByUsername(login);
        if(user == null) {
            throw new EmailNotRegisteredException();
        } else if(!user.isConfirmed()) {
            throw new EmailNotConfirmedException();
        }

        return true;
    }

    public boolean isUserNotConfirmed(String login) throws UserAlredyConfirmedException {
        var user = (UserEntity) loadUserByUsername(login);
        if(user.isConfirmed()) {
            throw new UserAlredyConfirmedException();
        }

        return true;
    }
}
