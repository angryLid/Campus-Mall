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

}
