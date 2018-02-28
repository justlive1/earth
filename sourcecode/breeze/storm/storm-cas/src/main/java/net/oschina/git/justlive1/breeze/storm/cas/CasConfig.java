package net.oschina.git.justlive1.breeze.storm.cas;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import net.oschina.git.justlive1.breeze.snow.common.base.util.HttpUtils;

/**
 * cas扩展配置类
 * 
 * @author wubo
 *
 */
@Configuration("stormCasConfig")
public class CasConfig {

  @PostConstruct
  void init() {
    HttpUtils.trustAllManager();
  }

}
