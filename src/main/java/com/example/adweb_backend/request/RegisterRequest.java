package com.example.adweb_backend.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.example.adweb_backend.validation.UserValidation.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = NOTNULL_MSG)
    @Pattern(regexp = USERNAME_REGEX, message = USERNAME_MSG)
    private String username;

    @NotNull(message = NOTNULL_MSG)
    @Pattern(regexp = NICKNAME_REGEX, message = NICKNAME_MSG)
    private String nickname;

    @NotNull(message = NOTNULL_MSG)
    @Pattern(regexp = PHONE_REGEX, message = PHONE_MSG)
    private String phone;

    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_MSG)
    private String email;

    @NotNull(message = NOTNULL_MSG)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_MSG)
    private String password;
}
