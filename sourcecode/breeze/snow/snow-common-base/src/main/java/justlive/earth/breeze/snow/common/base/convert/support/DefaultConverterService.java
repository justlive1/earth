package justlive.earth.breeze.snow.common.base.convert.support;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Maps;
import justlive.earth.breeze.snow.common.base.convert.Converter;
import justlive.earth.breeze.snow.common.base.convert.ConverterFactory;
import justlive.earth.breeze.snow.common.base.convert.ConverterRegistry;
import justlive.earth.breeze.snow.common.base.convert.ConverterService;
import justlive.earth.breeze.snow.common.base.util.Checks;

/**
 * 默认转换服务实现类
 * 
 * @author wubo
 *
 */
public class DefaultConverterService implements ConverterService, ConverterRegistry {

  /**
   * 转换器集合
   */
  private Map<ConverterTypePair, Converter<Object, Object>> converters = Maps.newHashMap();

  @SuppressWarnings("unchecked")
  @Override
  public ConverterRegistry addConverter(Converter<?, ?> converter) {
    Checks.notNull(converter);
    converters.put(converter.pair(), (Converter<Object, Object>) converter);
    return this;
  }

  @Override
  public ConverterRegistry addConverterFactory(ConverterFactory<?, ?> factory) {
    Checks.notNull(factory);
    List<Converter<Object, Object>> list = factory.converters();
    for (Converter<?, ?> converter : list) {
      addConverter(converter);
    }
    return this;
  }

  @Override
  public boolean canConverter(Class<?> source, Class<?> target) {
    return converters.containsKey(ConverterTypePair.create(source, target));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T convert(Object source, Class<T> targetType) {
    Converter<Object, Object> converter =
        converters.get(ConverterTypePair.create(source.getClass(), targetType));

    return (T) Checks.notNull(converter).convert(source);
  }

}
