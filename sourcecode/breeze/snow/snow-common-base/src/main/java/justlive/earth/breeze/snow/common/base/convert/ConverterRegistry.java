package justlive.earth.breeze.snow.common.base.convert;

/**
 * 转换器注册
 * 
 * @author wubo
 *
 */
public interface ConverterRegistry {

  /**
   * 注册转换器
   */
  ConverterRegistry addConverter(Converter<?, ?> converter);

  /**
   * 注册转换器工厂
   */
  ConverterRegistry addConverterFactory(ConverterFactory<?, ?> factory);
}
