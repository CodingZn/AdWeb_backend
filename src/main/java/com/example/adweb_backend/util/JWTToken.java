package com.example.adweb_backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.adweb_backend.mybatis.po.User;

import java.util.Calendar;

public class JWTToken {
    public static final String secret = "adweb3d";

    public static String getJWT(User user){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);

        return JWT.create()
                .withClaim("profileID", user.getProfileID())
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("nickname", user.getNickname())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(secret));

    }
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

}
