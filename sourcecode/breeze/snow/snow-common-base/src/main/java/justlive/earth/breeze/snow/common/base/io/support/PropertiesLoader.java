package justlive.earth.breeze.snow.common.base.io.support;

import java.io.IOException;
import java.util.Properties;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import justlive.earth.breeze.snow.common.base.util.PlaceHolderHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * properties配置文件加载器<br>
 * 支持classpath下配置文件，例如 classpath:/config/dev.properties,classpath*:/config/*.properties<br>
 * 支持文件系统下配置文件，例如 file:/home/dev.properties, file:D:/conf/dev.properties<br>
 * 支持配置文件中使用${k1:key}
 * 
 * @author wubo
 *
 */
@Slf4j
public class PropertiesLoader extends AbstractResourceLoader {

  private static final PlaceHolderHelper HELPER = new PlaceHolderHelper(
      PlaceHolderHelper.DEFAULT_PLACEHOLDER_PREFIX, PlaceHolderHelper.DEFAULT_PLACEHOLDER_SUFFIX,
      PlaceHolderHelper.DEFAULT_VALUE_SEPARATOR, true);

  /**
   * 配置路径
   */
  private String[] locations;

  private Properties props = new Properties();

  /**
   * 使用路径创建{@code PropertiesLoader}
   * 
   * @param locations
   */
  public PropertiesLoader(String... locations) {
    this(ClassLoader.getSystemClassLoader(), locations);
  }

  /**
   * 使用路径创建{@code PropertiesLoader}
   * 
   * @param loader
   * @param locations
   */
  public PropertiesLoader(ClassLoader loader, String... locations) {
    this.locations = locations;
    this.loader = loader;
  }

  /**
   * 获取属性值
   * 
   * @return
   */
  public Properties props() {
    return this.props;
  }

  @Override
  public void init() {
    this.resources.addAll(this.parse(this.locations));
    for (SourceResource resource : this.resources) {
      try {
        props.load(this.getReader(resource));
      } catch (IOException e) {
        if (log.isDebugEnabled()) {
          log.debug("resource [{}] read error", resource.path(), e);
        }
        if (!ignoreNotFound) {
          throw Exceptions.wrap(e);
        }
      }
    }
  }

  public String getProperty(String key) {
    String value = props.getProperty(key);
    if (value == null) {
      return value;
    }
    return HELPER.replacePlaceholders(value, props);
  }

  public String getProperty(String key, String defaultValue) {
    String value = props.getProperty(key, defaultValue);
    if (value == null) {
      return value;
    }
    return HELPER.replacePlaceholders(value, props);
  }
}
