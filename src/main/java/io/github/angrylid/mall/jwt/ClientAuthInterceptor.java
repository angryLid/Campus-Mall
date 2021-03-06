package io.github.angrylid.mall.jwt;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.UserService;

@Component
public class ClientAuthInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String token = request.getHeader("auth");
        Method method = handlerMethod.getMethod();

        if (token != null && !token.isEmpty() && !token.isBlank()) {
            try {
                if (!jwtUtil.verify(token)) {
                    throw new IllegalArgumentException(token + "不合法，未通过认证，请重新登录");
                }
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException("未通过认证，请重新登录");
            }

            try {
                String telephone = JWT.decode(token).getClaim("telephone").asString();
                User user = this.userService.getUserByTel(telephone);
                if (user == null) {
                    throw new IllegalArgumentException("用户不存在");
                }
                logger.error("Access {}", user.getId());
                request.setAttribute("id", user.getId());

            } catch (JWTDecodeException e) {
                throw new IllegalArgumentException("用户不存在");
            }

            // if (method.isAnnotationPresent(TokenRequired.class)) {
            // TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            // if (userLoginToken.role() == UserRole.ADMIN) {
            // if (user.getRoleType() != "1") {
            // throw new IllegalArgumentException("无权访问");
            // }
            // }

            // if (userLoginToken.role() == UserRole.STAFF) {
            // request.setAttribute("UserIdentity", user.getId());
            // }
            // }

            return true;
        } else {
            if (method.isAnnotationPresent(TokenRequired.class)) {
                throw new IllegalArgumentException("请先登录");
            } else {
                request.setAttribute("id", null);
            }
        }

        return true;
    }

}
