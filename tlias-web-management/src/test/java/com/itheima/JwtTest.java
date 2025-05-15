package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    // Jwts.builder()生成Jwt令牌
    // Jwts.parser()解析Jwt令牌
    /**
     * 生成Jwt令牌, 并设置过期时间
     */
    @Test
    public void test() {
//        String token = JwtUtil.createToken("admin", "admin", 1000 * 60 * 60 * 24 * 7);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "axRoZWltYQ==")
                .addClaims(dataMap)  // 添加自定义数据
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 设置过期时间
                .compact();//  生成token
        System.out.println(jwt);
    }

    /**
     * 解析Jwt令牌
     */
    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc0NjQ2MzE1Nn0.OXK3eqC4ZBIN5Z6cFvlSXkFtZoeWO4GHUvBNwgH7c-Y";
        Claims body = Jwts.parser()// 解析Jwt令牌
                .setSigningKey("axRoZWltYQ==")// 设置签名密钥
                .parseClaimsJws(token)//  解析Jwt令牌
                .getBody();// 获取Jwt令牌中的数据
        System.out.println(body);
    }

}
