package com.bocft.meeting.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {
    private static final String SIGN = "8677df7fc3a34e26a61c034d5ec8245d";

    //创建token
    public static String createJWT(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //默认过期时间7天
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payload令牌
        map.forEach(builder::withClaim);

        String token = builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .withIssuer("bocft")  //指定签发人
                .sign(Algorithm.HMAC256(SIGN));  //加密算法和密钥
        return token;
    }

    //验证token合法性
    public static void verity(String token) {
        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    //获取 token 信息
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
