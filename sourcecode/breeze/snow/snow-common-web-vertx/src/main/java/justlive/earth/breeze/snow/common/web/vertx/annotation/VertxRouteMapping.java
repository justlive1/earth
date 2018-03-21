package justlive.earth.breeze.snow.common.web.vertx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.vertx.core.http.HttpMethod;

/**
 * route的请求映射
 * 
 * @author wubo
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxRouteMapping {
  /**
   * 地址映射 (e.g. "/myPath.do").
   * 
   * @return
   */
  String[] value() default {};

  /**
   * HTTP请求类型 :GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE.
   * 
   * @return
   */
  HttpMethod[] method() default {};

  /**
   * 指定处理请求的提交内容类型(Content-Type) *
   * 
   * <pre class="code">
   * consumes = "text/plain"
   * consumes = {"text/plain", "application/*"}
   * </pre>
   * 
   * @return
   */
  String[] consumes() default {};

  /**
   * 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回
   * 
   * 
   * <pre class="code">
   * produces = "text/plain"
   * produces = {"text/plain", "application/*"}
   * produces = "application/json; charset=UTF-8"
   * </pre>
   * 
   * @return
   */
  String[] produces() default {};

  /**
   * 是否使用阻塞方式
   */
  boolean blocking() default false;
}
