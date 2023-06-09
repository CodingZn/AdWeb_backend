package com.example.adweb_backend.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.example.adweb_backend.validation.UserValidation.*;
import static com.example.adweb_backend.validation.UserValidation.PASSWORD_MSG;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @Pattern(regexp = USERNAME_REGEX, message = USERNAME_MSG)
    private String username;

    @NotNull
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_MSG)
    private String password;
}
