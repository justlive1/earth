package net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于Route解析http body绑定到注解参数上
 * 
 * @author wubo
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxRequestBody {

  /**
   * 是否必须
   * 
   * @return
   */
  boolean required() default true;

}
