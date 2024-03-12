package com.example.demo.anno;

import com.example.demo.validation.StateVaildation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented//元注解
@Target({ FIELD})//元注解
@Retention(RUNTIME)//元注解
@Constraint(validatedBy = {StateVaildation.class})//指定提供校验规则的类
public @interface State {
    //提供校验失败后的信息
    String message() default "{state参数只能是已发布或者草稿}";

    //指定分组
    Class<?>[] groups() default {};

    //负载  获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};

}