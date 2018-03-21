package justlive.earth.breeze.snow.common.base.convert.support;

import java.util.Map;
import com.google.common.collect.Maps;
import justlive.earth.breeze.snow.common.base.convert.Converter;
import justlive.earth.breeze.snow.common.base.convert.ConverterFactory;
import justlive.earth.breeze.snow.common.base.convert.ConverterRegistry;
import justlive.earth.breeze.snow.common.base.convert.ConverterService;

/**
 * 默认转换服务实现类
 * 
 * @author Administrator
 *
 */
public class DefaultConverterServiceImpl implements ConverterService, ConverterRegistry {

  /**
   * 转换器
   */
  private Map<ConverterTypePair, Converter<?, ?>> converters = Maps.newHashMap();

  @Override
  public void addConverter(Converter<?, ?> converter) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addConverterFactory(ConverterFactory<?, ?> factory) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean canConverter(Class<?> source, Class<?> target) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public <T> T convert(Object source, Class<T> targetType) {
    // TODO Auto-generated method stub
    return null;
  }

}
