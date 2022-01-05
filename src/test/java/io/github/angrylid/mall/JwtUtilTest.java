package io.github.angrylid.mall;

import io.github.angrylid.mall.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtUtilTest {

    @Test
    public void testSignAndVerify(){
        var jwt = JwtUtil.sign("17132954889","12345678");
        var verified = JwtUtil.verify(jwt);

        assertTrue(verified);
    }
}
