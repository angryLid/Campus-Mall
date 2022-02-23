package io.github.angrylid.mall.jwt;

import java.util.Date;
import java.util.HashMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private long EXPIRE_TIME;

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * 签发用户token
     * 
     * @param telephone 用户名
     * @param password  密码
     * @return 签发的token
     */
    public String sign(String telephone, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create()
                .withHeader(header)
                .withClaim("telephone", telephone)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证用户token
     * 
     * @param token 签发的token
     * @return 是否合法，是否过期
     */
    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 签发后台管理员
     * 
     * @param username 用户名
     * @param password 密码
     * @return JWT
     */
    public String signAdmin(String username, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create()
                .withHeader(header)
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证后台管理员身份
     * 
     * @param token JWT
     * @return 是否合法
     */
    public boolean verifyAdmin(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

}
