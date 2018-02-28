package net.oschina.git.justlive1.breeze.storm.cas.management;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import net.oschina.git.justlive1.breeze.snow.common.base.util.HttpUtils;

/**
 * cas扩展配置类
 * 
 * @author wubo
 *
 */
@Configuration("stormCasManagementConfig")
public class CasManagementConfig {

  @PostConstruct
  void init() {
    HttpUtils.trustAllManager();
  }
}
