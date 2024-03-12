package com.example.demo.validation;

import com.example.demo.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateVaildation implements ConstraintValidator<State,String> {//给哪个注解提供校验规则，校验规则的类型

    /*
    *
    * @param value 将要校验的数据
    * @param context
    *
    * @return 如果返回false，则是校验失败，return ture则校验通过
    *
    * */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验的数据
        if(value == null){
            return false;
        }
        if(value.equals("已发布") ||value.equals("草稿")){
            return true;
        }

        return false;
    }
}
