package justlive.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import io.vertx.ext.web.RoutingContext;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxHeaderParam;

/**
 * header参数解析器
 * 
 * @author wubo
 *
 */
public class HeaderParamResolver implements MethodParamResolver {

  @Override
  public boolean supported(Parameter parameter) {
    return parameter.isAnnotationPresent(VertxHeaderParam.class);
  }

  @Override
  public ParamWrap resolve(Parameter parameter) {
    VertxHeaderParam annotation = parameter.getAnnotation(VertxHeaderParam.class);
    return new ParamWrap(annotation.value(), annotation.required(), 0, parameter.getType());
  }

  @Override
  public Object render(ParamWrap wrap, RoutingContext ctx) {
    return ctx.request().getHeader(wrap.getValue());
  }

}
