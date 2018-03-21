package justlive.earth.breeze.snow.common.base.convert.support;

import java.util.Set;
import com.google.common.collect.ImmutableSet;
import justlive.earth.breeze.snow.common.base.convert.Converter;

/**
 * String -> Boolean 转换器<br>
 * 注意: null和空字符串被解析为false
 * 
 * 
 * @author wubo
 *
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {

  /**
   * 表示true的字符值
   */
  private static final Set<String> TRUE_VALUES = ImmutableSet.of("true", "on", "yes", "1");

  /**
   * 表示false的字符值
   */
  private static final Set<String> FALSE_VALUES = ImmutableSet.of("false", "off", "no", "0");


  @Override
  public Boolean convert(String source) {

    String value = source;
    if (value == null || value.trim().isEmpty()) {
      return Boolean.FALSE;
    }

    value = value.toLowerCase();

    if (TRUE_VALUES.contains(value)) {
      return Boolean.TRUE;
    } else if (FALSE_VALUES.contains(value)) {
      return Boolean.FALSE;
    } else {
      throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
    }
  }

  @Override
  public ConverterTypePair pair() {
    return ConverterTypePair.create(String.class, Boolean.class);
  }
}
