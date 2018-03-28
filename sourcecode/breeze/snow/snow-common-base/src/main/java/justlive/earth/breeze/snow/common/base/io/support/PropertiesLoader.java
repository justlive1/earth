package justlive.earth.breeze.snow.common.base.io.support;

import java.io.IOException;
import java.util.Properties;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import lombok.extern.slf4j.Slf4j;

/**
 * properties配置文件加载器<br>
 * 支持classpath下配置文件，例如 classpath:/config/dev.properties,classpath*:/config/*.properties<br>
 * 支持文件系统下配置文件，例如 file:/home/dev.properties, file:D:/conf/dev.properties
 * 
 * @author wubo
 *
 */
@Slf4j
public class PropertiesLoader extends AbstractResourceLoader {

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
    this.init();
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
  protected void init() {
    this.resources.addAll(this.parse(this.locations));
    for (SourceResource resource : this.resources) {
      try {
        // TODO 设置编码
        props.load(resource.getInputStream());
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("resource [{}] read error", resource.path(), e);
        }
      }
    }
  }

}
