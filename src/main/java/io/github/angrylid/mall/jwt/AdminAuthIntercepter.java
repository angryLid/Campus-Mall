
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

import io.github.angrylid.mall.generated.entity.Admin;
import io.github.angrylid.mall.jwt.annotation.AdminRequired;
import io.github.angrylid.mall.service.AdminService;

@Component
public class AdminAuthIntercepter implements HandlerInterceptor {
    @Autowired
    private AdminService adminService;

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

        if (method.isAnnotationPresent(AdminRequired.class)) {

            if (token == null) {
                throw new IllegalArgumentException("请先登录");
            }

            try {
                if (!JwtUtil.verifyAdmin(token)) {
                    throw new IllegalArgumentException("Token不合法, 请重新登录");
                }
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException("验证出现异常, 请重新登录");
            }

            try {
                String name = JWT.decode(token).getClaim("username").asString();
                String password = JWT.decode(token).getClaim("password").asString();
                Admin admin = adminService.selectAdmin(name, password);

                if (admin == null) {
                    throw new IllegalArgumentException("未能找到管理员");
                }

            } catch (JWTDecodeException e) {
                throw new IllegalArgumentException("Token解码异常, 管理员不存在");
            }

            // request.setAttribute("id", user.getId());

            // TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            // if (userLoginToken.role() == UserRole.ADMIN) {
            // if (user.getRoleType() != "1") {
            // throw new IllegalArgumentException("无权访问");
            // }
            // }

            // if (userLoginToken.role() == UserRole.STAFF) {
            // request.setAttribute("UserIdentity", user.getId());
            // }

            return true;
        }
        return true;
    }

}
