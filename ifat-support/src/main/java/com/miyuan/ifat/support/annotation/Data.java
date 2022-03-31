package com.miyuan.ifat.support.annotation;

import org.testng.annotations.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE})
public @interface Data{
    String dataFile() default "";
    String testName() default "";
    String commonFile() default "";
}
