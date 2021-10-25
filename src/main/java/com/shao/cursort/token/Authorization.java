package com.shao.cursort.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  * 在 Controller 的方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回 401 错误  * @author ScienJus  * @date 2015/7/31.
 * 在 Controller 的方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回 401 错误
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
