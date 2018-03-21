package justlive.earth.breeze.lighting.chained.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 核心配置
 * 
 * @author wubo
 *
 */
@Profile("default")
@Configuration
public class DefaultConf {

  @Bean
  @ConfigurationProperties(prefix = "lighting.core")
  public CoreProps coreProps() {
    return new CoreProps();
  }

  @Bean
  @ConfigurationProperties(prefix = "lighting.project")
  public ProjectProps projectProps() {
    return new ProjectProps();
  }

}
