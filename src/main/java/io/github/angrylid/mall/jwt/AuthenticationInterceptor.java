package io.github.angrylid.mall.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.mbg.entity.User;
import io.github.angrylid.mall.service.UserService;
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

            if (token == null) {
                throw new IllegalArgumentException("请先登录");
            }

            try {
                if (!JwtUtil.verity(token)) {
                    throw new IllegalArgumentException("未通过认证，请重新登录");
                }
            } catch (JWTVerificationException e) {
                throw new IllegalArgumentException("未通过认证，请重新登录");
            }

            String jobNumber;
            try {
                jobNumber = JWT.decode(token).getClaim("jobNumber").asString();
            } catch (JWTDecodeException e) {
                throw new IllegalArgumentException("用户不存在");
            }
            User user = userService.queryUserById(Long.parseLong(jobNumber));
            if (user == null) {
                throw new IllegalArgumentException("用户不存在");
            }

            request.setAttribute("identity",user.getId());

            TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            if (userLoginToken.role() == UserRole.ADMIN) {
                if (user.getIsAdmin() != 1) {
                    throw new IllegalArgumentException("无权访问");
                }
            }

            if(userLoginToken.role() == UserRole.STAFF){
                request.setAttribute("UserIdentity",user.getId());
            }

            return true;
        }
        return true;
    }

}
