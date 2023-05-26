package com.example.adweb_backend.mybatis.po;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private int id;
    private String salt;
    private String passwd;
}
