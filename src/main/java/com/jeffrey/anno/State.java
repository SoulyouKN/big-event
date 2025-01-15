package com.jeffrey.anno;

import com.jeffrey.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//自定义注解
@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({FIELD})//元注解,用在哪
@Retention(RUNTIME)//元注解,用在什么时候

public @interface State {
    //校验失败后的提示信息
    String message() default "state的值只能是已发布或草稿";

    //指定分组
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
