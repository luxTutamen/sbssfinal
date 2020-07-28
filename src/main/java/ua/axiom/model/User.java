package ua.axiom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@Table(name = "UUSERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 4)
    private UserLocale locale;

    private boolean isBanned;

    protected abstract void setNotNullableFields(Object ... data);

    public User() {}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isBanned;
    }
}
