package com.example.adweb_backend.mybatis.po;


import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.adweb_backend.validation.UserValidation.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    @Pattern(regexp = USERNAME_REGEX, message = USERNAME_MSG)
    private String username;
    @Pattern(regexp = NICKNAME_REGEX, message = NICKNAME_MSG)
    private String nickname;
    @Pattern(regexp = PHONE_REGEX, message = PHONE_MSG)
    private String phone;
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_MSG)
    private String email;

    @Size(max = 255, min = 1)
    private String salt;
    @Size(max = 255, min = 1)
    private String passwd;

    private int profileID=0;
}
