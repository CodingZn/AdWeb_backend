package com.example.adweb_backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTToken {
    public static final String secret = "adweb3d";

    public static String getJWT(int id, String username, String nickname){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);

        return JWT.create()
                .withClaim("username", username)
                .withClaim("id", id)
                .withClaim("nickname", nickname)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(secret));

    }
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

}
