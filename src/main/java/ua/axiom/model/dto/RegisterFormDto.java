package ua.axiom.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterFormDto {
    @Pattern(regexp="{regex.password}")
    private String password;

    @Pattern(regexp="{regex.login}")
    private String login;

    @NotNull
    private String role;

    @NotNull
    private String locale;
}
