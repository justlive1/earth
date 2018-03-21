package justlive1.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Method;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRouteMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Route包装类，用于封装解析注解后的路由实现
 * 
 * @author wubo
 *
 */
@Data
@AllArgsConstructor
public class RouteWrap {

  String url;

  Method method;

  Object bean;

  ParamWrap[] paramWraps;

  VertxRouteMapping routeMapping;
}
