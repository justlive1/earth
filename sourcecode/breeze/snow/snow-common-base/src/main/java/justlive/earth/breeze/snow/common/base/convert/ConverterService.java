package justlive.earth.breeze.snow.common.base.convert;


/**
 * 类型转换服务
 * 
 * @author wubo
 *
 */
public interface ConverterService {

  /**
   * 类型是否支持转换
   * 
   * @param source
   * @param target
   * @return
   */
  boolean canConverter(Class<?> source, Class<?> target);

  /**
   * 转换到指定类型
   * 
   * @param source
   * @param targetType
   * @return
   */
  <T> T convert(Object source, Class<T> targetType);
}
