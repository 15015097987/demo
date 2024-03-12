package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JWTest {
    @Test//测试的头
    public void testGen() {
        Map<String, Object> clams = new HashMap<>();
        clams.put("id", 1);
        clams.put("username", "张三");
        //生成jwt的代码
        String token = JWT.create()
                .withClaim("user", clams)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))//添加过期时间;
                .sign(Algorithm.HMAC256("yepeiming"));//添加密钥,指定算法
        System.out.println(token);


    }
    @Test
    public void testParse() {
        //定义字符串,模拟用户传递过来的token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJleHAiOjE3MDgxMTAzMDQsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19." +
                "33t0yx0kmjrJTT1SclnxJXPL2RSD0Iq5H_FCBCNs3Zk";

        //调用JWT
        JWTVerifier yepeiming = JWT.require(Algorithm.HMAC256("yepeiming")).build();

        DecodedJWT decodedJWT = yepeiming.verify(token);//验证token生成一个解析后的JWT对象

        Map<String,Claim> claims= decodedJWT.getClaims();
        System.out.println(claims.get("user"));

        //如果串改了头部和载荷和密钥的数据,那么验证失败,过期了也是失败

    }


}
