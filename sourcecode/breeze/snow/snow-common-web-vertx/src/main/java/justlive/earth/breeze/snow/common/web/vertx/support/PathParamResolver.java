package justlive.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import io.vertx.ext.web.RoutingContext;
import justlive.earth.breeze.snow.common.web.vertx.annotation.VertxPathParam;

/**
 * url路径参数解析器
 * 
 * @author wubo
 *
 */
public class PathParamResolver extends AbastractConverterParamResolver {

  @Override
  public boolean supported(Parameter parameter) {
    return parameter.isAnnotationPresent(VertxPathParam.class);
  }

  @Override
  public ParamWrap resolve(Parameter parameter) {
    VertxPathParam annotation = parameter.getAnnotation(VertxPathParam.class);
    return new ParamWrap(annotation.value(), annotation.required(), 0, parameter.getType());
  }

  @Override
  public Object render(ParamWrap wrap, RoutingContext ctx) {
    return converter(ctx.request().getParam(wrap.getValue()), wrap.getClazz());
  }

}
