package justlive.earth.breeze.snow.common.base.io.support;

import java.util.Properties;
import justlive.earth.breeze.snow.common.base.util.PathMatcher;

/**
 * properties配置文件加载器
 * 
 * @author wubo
 *
 */
public class PropertiesLoader {

  /**
   * 配置路径
   */
  private String[] locations;

  private Properties props;

  private PathMatcher matcher = new PathMatcher();

  /**
   * 使用路径创建{@code PropertiesLoader}
   * 
   * @param locations
   */
  public PropertiesLoader(String... locations) {
    this.locations = locations;
  }


}
