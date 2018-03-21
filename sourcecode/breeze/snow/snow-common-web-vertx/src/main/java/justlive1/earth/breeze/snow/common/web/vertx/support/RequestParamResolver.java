package justlive1.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import io.vertx.ext.web.RoutingContext;
import justlive1.earth.breeze.snow.common.web.vertx.annotation.VertxRequestParam;

/**
 * 请求参数解析器
 * 
 * @author wubo
 *
 */
public class RequestParamResolver implements MethodParamResolver {

  @Override
  public boolean supported(Parameter parameter) {
    return parameter.isAnnotationPresent(VertxRequestParam.class);
  }

  @Override
  public ParamWrap resolve(Parameter parameter) {
    VertxRequestParam annotation = parameter.getAnnotation(VertxRequestParam.class);
    return new ParamWrap(annotation.value(), annotation.required(), 0, parameter.getType());
  }

  @Override
  public Object render(ParamWrap wrap, RoutingContext ctx) {
    return ctx.request().getParam(wrap.getValue());
  }

}
