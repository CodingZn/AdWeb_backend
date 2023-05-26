package com.example.adweb_backend.mybatis.po;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private int authid;
}
