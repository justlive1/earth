package justlive1.earth.breeze.snow.common.web.vertx.support;

import java.lang.reflect.Parameter;
import io.vertx.ext.web.RoutingContext;

/**
 * 方法参数处理器
 * 
 * @author wubo
 *
 */
public interface MethodParamResolver {

  /**
   * 当前方法参数是否支持
   * 
   * @param parameter
   * @return
   */
  boolean supported(Parameter parameter);

  /**
   * 解析当前方法参数
   * 
   * @param parameter
   * @return
   */
  ParamWrap resolve(Parameter parameter);

  /**
   * 渲染获取当前参数值
   * 
   * @param wrap
   * @param ctx
   * @return
   */
  Object render(ParamWrap wrap, RoutingContext ctx);
}
