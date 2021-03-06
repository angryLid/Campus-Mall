package io.github.angrylid.mall.jwt.annotation;

import io.github.angrylid.mall.jwt.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenRequired {
    boolean required() default true;

    UserRole role() default UserRole.STAFF;
}
