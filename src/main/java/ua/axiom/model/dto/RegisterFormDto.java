package ua.axiom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.axiom.model.Role;
import ua.axiom.model.UserLocale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFormDto {
    @NotNull
    @Pattern(regexp = "([\\w\\d_-]){6,40}", message = "correct input expected, 6 to 40 characters")
    private String username;

    @NotNull
    @Pattern(regexp = "([A-Za-z\\d!@#$%^&*()]){8,40}", message = "correct input expected, 8 to 40 characters")
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private UserLocale locale;
}
