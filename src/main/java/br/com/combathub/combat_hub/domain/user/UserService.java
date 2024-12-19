package br.com.combathub.combat_hub.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserEntity registerUser(UserEntity user) {
        return repository.save(user);
    }

}
