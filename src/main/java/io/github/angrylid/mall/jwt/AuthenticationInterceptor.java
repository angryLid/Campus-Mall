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
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        logger.error("Access {}", getClass());
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String token = request.getHeader("auth");

        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenRequired.class)) {

            if (token == null) {
                throw new IllegalArgumentException("请先登录");
            }

            try {
                if (!JwtUtil.verify(token)) {
                    throw new IllegalArgumentException(token + "不合法，未通过认证，请重新登录");
                }
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException("未通过认证，请重新登录");
            }

            String telephone;
            try {
                telephone = JWT.decode(token).getClaim("telephone").asString();
            } catch (JWTDecodeException e) {
                throw new IllegalArgumentException("用户不存在");
            }

            User user = this.userService.getUserByTel(telephone);

            if (user == null) {
                throw new IllegalArgumentException("用户不存在");
            }

            request.setAttribute("id", user.getId());

            TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            if (userLoginToken.role() == UserRole.ADMIN) {
                if (user.getRoleType() != "1") {
                    throw new IllegalArgumentException("无权访问");
                }
            }

            if (userLoginToken.role() == UserRole.STAFF) {
                request.setAttribute("UserIdentity", user.getId());
            }

            return true;
        }
        return true;
    }

}
