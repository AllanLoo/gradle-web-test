package com.isp.common.web.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解当表单中有多个平级的实体时，用这个注解
 * 例如：控制层，用@CommonModel("deskEntity")DeskEntity deskEntity接受页面的参数
 * 页面表单deskEntity.id,deskEntity.name等
 * Created by AllanLoo on 2015/9/6.
 */
@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonModel {
    String value();

    String prefix() default ".";
}
