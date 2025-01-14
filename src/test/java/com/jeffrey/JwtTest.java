package com.jeffrey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String,Object> claims = new HashMap<>();
        claims.put("name","John");
        claims.put("email","john@example.com");
        String token = JWT.create()
                .withClaim("user",claims)//添加数据
                .withExpiresAt(new Date(System.currentTimeMillis()*1000*60*60))//设置过期时间机制
                .sign(Algorithm.HMAC256("Jeffrey"));//指定算法和密钥
        System.out.println(token);

    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7Im5hbWUiOiJKb2huIiwiZW1haWwiOiJqb2huQGV4YW1wbGUuY29tIn0sImV4cCI6NjI1MjY5NTYwMDAxODQwMH0." +
                "yOOW1rM-BqRKInBPdDjEQnlM-8lbi-cBn4idonz0Zs0";
        JWTVerifier jwtverifier = JWT.require(Algorithm.HMAC256("Jeffrey")).build();
        DecodedJWT decodedJWT = jwtverifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
//    验证失败的情形:1.token被改 2.密钥被改 3.token过期

}
