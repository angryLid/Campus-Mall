package io.spring.guides.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.spring.guides.annotation.TokenRequired;
import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenRequired.class)) {
//            TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            if (token == null) {
//                request.getRequestDispatcher("/api/error/invalidtoken").forward(request, response);

//                return false;
                throw new IllegalArgumentException("登录过期");
            }
            String userId;
            try {
                userId = JWT.decode(token).getClaim("userId").asString();
            } catch (JWTDecodeException e) {
                throw new IllegalArgumentException("用户不存在");
            }
            User user = userService.queryUserById(Long.parseLong(userId));
            if (user == null) {
                throw new IllegalArgumentException("用户不存在");
            }
            try {
                if (!JwtUtil.verity(token, user.getPasswd())) {
                    throw new IllegalArgumentException("用户不合法");
                }
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException("用户不合法");
            }
            return true;
        }
        return true;
    }

}
