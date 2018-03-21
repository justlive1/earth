package justlive.earth.breeze.snow.common.base.convert.support;

import justlive.earth.breeze.snow.common.base.convert.Converter;
import justlive.earth.breeze.snow.common.base.convert.ConverterFactory;
import justlive.earth.breeze.snow.common.base.util.NumberUtils;

/**
 * String -> Number 解析器
 * 
 * @author wubo
 * @see java.lang.Byte
 * @see java.lang.Short
 * @see java.lang.Integer
 * @see java.lang.Long
 * @see java.math.BigInteger
 * @see java.lang.Float
 * @see java.lang.Double
 * @see java.math.BigDecimal
 * @param <T>
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

  @Override
  public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
    return new StringToNumber<>(targetType);
  }

  private static final class StringToNumber<T extends Number> implements Converter<String, T> {

    private final Class<T> targetType;

    public StringToNumber(Class<T> targetType) {
      this.targetType = targetType;
    }

    @Override
    public T convert(String source) {
      if (source == null || source.isEmpty()) {
        return null;
      }
      return NumberUtils.parseNumber(source, this.targetType);
    }

    @Override
    public ConverterTypePair pair() {
      return ConverterTypePair.create(String.class, targetType);
    }
  }
}
