package justlive.earth.breeze.snow.common.web.vertx.support;

import justlive.earth.breeze.snow.common.base.convert.ConverterService;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;
import justlive.earth.breeze.snow.common.web.vertx.exception.ErrorCodes;

/**
 * 参数类型转换解析器
 * 
 * @author wubo
 *
 */
public abstract class AbastractConverterParamResolver implements MethodParamResolver {

  protected ConverterService converterService;

  public AbastractConverterParamResolver converterService(ConverterService converterService) {
    this.converterService = converterService;
    return this;
  }

  @SuppressWarnings("unchecked")
  protected <T> T converter(String source, Class<T> targetType) {
    if (targetType == String.class) {
      return (T) source;
    }
    if (converterService.canConverter(String.class, targetType)) {
      return converterService.convert(source, targetType);
    }
    throw Exceptions.fail(ErrorCodes.TYPE_CANNOT_CONVERTER, source, targetType);
  }
}
