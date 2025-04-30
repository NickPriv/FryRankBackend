package com.fryrank.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenUtils {
    private TokenUtils(){
    }

    public static String decodeToken(String token, String token_key ) {
        Claims claims = Jwts.parserBuilder().setSigningKey(token_key.getBytes()).build().parseClaimsJws(token).getBody();
        return claims.get("userId", String.class);
    }
}
