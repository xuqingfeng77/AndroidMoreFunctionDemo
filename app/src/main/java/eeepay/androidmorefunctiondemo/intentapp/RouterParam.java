package eeepay.androidmorefunctiondemo.intentapp;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xuqingfeng on 2017/2/22.
 */

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface RouterParam {
    String value() default "";
}
