package com.example.adweb_backend.mybatis.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private int id;
    private String salt;
    private String passwd;
}
