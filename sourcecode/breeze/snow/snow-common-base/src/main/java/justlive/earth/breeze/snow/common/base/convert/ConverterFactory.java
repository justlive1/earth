package justlive.earth.breeze.snow.common.base.convert;

import java.util.List;

/**
 * 转换工厂，用于将S转换成R的子类
 * 
 * @author wubo
 *
 * @param <S>
 * @param <R>
 */
public interface ConverterFactory<S, R> {

  /**
   * 获取转换器
   * 
   * @param targetType
   * @return
   */
  <T extends R> Converter<S, T> getConverter(Class<T> targetType);

  /**
   * 获取所有支持的转换器
   * 
   * @return
   */
  List<Converter<Object, Object>> converters();
}
