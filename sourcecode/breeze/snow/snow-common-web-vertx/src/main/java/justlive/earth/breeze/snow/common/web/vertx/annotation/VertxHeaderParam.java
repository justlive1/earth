package justlive.earth.breeze.snow.common.web.vertx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于Route绑定方法上的入参到request header
 * 
 * @author wubo
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxHeaderParam {

  /**
   * 参数名称
   * 
   * @return
   */
  String value() default "";

  /**
   * 是否必须
   * 
   * @return
   */
  boolean required() default true;
}
