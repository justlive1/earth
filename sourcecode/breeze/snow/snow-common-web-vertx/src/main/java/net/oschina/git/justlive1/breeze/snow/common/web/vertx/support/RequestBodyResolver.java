package net.oschina.git.justlive1.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import io.vertx.ext.web.RoutingContext;
import net.oschina.git.justlive1.breeze.snow.common.web.vertx.annotation.VertxRequestBody;

/**
 * http body 解析器
 * 
 * @author wubo
 *
 */
public class RequestBodyResolver implements MethodParamResolver {

  @Override
  public boolean supported(Parameter parameter) {
    return parameter.isAnnotationPresent(VertxRequestBody.class);
  }

  @Override
  public ParamWrap resolve(Parameter parameter) {
    VertxRequestBody annotation = parameter.getAnnotation(VertxRequestBody.class);
    return new ParamWrap(null, annotation.required(), 0, parameter.getType());
  }

  @Override
  public Object render(ParamWrap wrap, RoutingContext ctx) {
    return ctx.getBodyAsJson().mapTo(wrap.getClazz());
  }

}
