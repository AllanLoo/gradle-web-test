package com.isp.common.persistence.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示Mybatis的dao，方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描
 * Created by allan on 15-6-22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisDao {

}
