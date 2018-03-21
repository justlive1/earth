package justlive.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import java.util.List;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;

/**
 * 组合参数解析器
 * 
 * @author wubo
 *
 */
@AllArgsConstructor
public class ParamResolverComposite implements MethodParamResolver {

  private List<MethodParamResolver> resolvers;

  @Override
  public boolean supported(Parameter parameter) {
    for (MethodParamResolver resolver : resolvers) {
      if (resolver.supported(parameter)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ParamWrap resolve(Parameter parameter) {
    for (int i = 0, len = resolvers.size(); i < len; i++) {
      if (resolvers.get(i).supported(parameter)) {
        ParamWrap wrap = resolvers.get(i).resolve(parameter);
        wrap.setIndex(i);
        return wrap;
      }
    }
    return null;
  }

  @Override
  public Object render(ParamWrap wrap, RoutingContext ctx) {
    return resolvers.get(wrap.getIndex()).render(wrap, ctx);
  }

}
