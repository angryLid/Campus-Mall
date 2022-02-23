
package io.github.angrylid.mall.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthIntercepter implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("auth");

        if (token == null || token.isEmpty() || token.isBlank()) {
            throw new IllegalArgumentException("请先登录");
        }

        try {
            if (!jwtUtil.verifyAdmin(token)) {
                throw new IllegalArgumentException("Token不合法, 请重新登录");
            }
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("验证出现异常, 请重新登录");
        }

        return true;
    }

}
