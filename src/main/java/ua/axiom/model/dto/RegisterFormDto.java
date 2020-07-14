package ua.axiom.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterFormDto {
    @NotNull
    private String password;

    @NotNull
    private String login;

    @NotNull
    private String role;

    @NotNull
    private String locale;
}
