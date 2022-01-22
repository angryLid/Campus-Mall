package io.github.angrylid.mall.jwt;

import java.util.Date;
import java.util.HashMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtUtil {
    // 365 Days
    private static final long EXPIRE_TIME = 31536000000L;
    private static final String SECRET = "8sn3D3v-:.f]3";

    /**
     * 签发用户token
     * 
     * @param telephone 用户名
     * @param password  密码
     * @return 签发的token
     */
    public static String sign(String telephone, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create()
                .withHeader(header)
                .withClaim("password", password)
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
    public static boolean verify(String token) {
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
    public static String signAdmin(String username, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256("SECRET");
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return JWT.create()
                .withHeader(header)
                .withClaim("password", password)
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
    public static boolean verifyAdmin(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("SECRET");
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

}
