package ua.axiom.model.objects;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "UUSERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 40)
    @NotBlank(message = "username is required")
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 4)
    private UserLocale locale;

    private boolean isBanned;

    public static User userFactory(String login, String password, String role, Object ... userSpecificData) throws RuntimeException {
        User user;
        switch (role) {
            case ("CLIENT"):{
                user = new Client();
                break;
            }
            case ("DRIVER"): {
                user = new Driver();
                break;
            }
            case ("ADMIN"):{
                user = new Admin();
                break;
            }
            default: {
                throw new RuntimeException("role string <" + role + "> doesn't name any role");
            }
        }

        user.setUsername(login);
        user.setPassword(password);

        user.setNotNullableFields(userSpecificData);

        return user;
    }

    protected abstract void setNotNullableFields(Object ... data);

    public void onLoginIn() {}

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
