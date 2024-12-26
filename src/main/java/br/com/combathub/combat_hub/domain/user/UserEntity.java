package br.com.combathub.combat_hub.domain.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String password;

    @Column(name="registered_at")
    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean confirmed;

    public UserEntity(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.registeredAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

    public UserEntity() {}

    public UserEntity(long id, String login, String password,
                      LocalDateTime registeredAt, UserRole role, boolean confirmed) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.registeredAt = registeredAt;
        this.role = role;
        this.confirmed = confirmed;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
