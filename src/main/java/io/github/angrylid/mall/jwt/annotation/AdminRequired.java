package io.github.angrylid.mall.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.angrylid.mall.jwt.UserRole;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminRequired {
    boolean required() default true;

    UserRole role() default UserRole.STAFF;
}
