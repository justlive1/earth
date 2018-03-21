package justlive1.earth.breeze.snow.common.base.convert;

/**
 * 类型转换器
 * 
 * @author wubo
 *
 * @param <S>
 * @param <T>
 */
@FunctionalInterface
public interface Converter<S, T> {

  /**
   * 类型转换
   * 
   * @param source
   * @return
   */
  T convert(S source);
}
