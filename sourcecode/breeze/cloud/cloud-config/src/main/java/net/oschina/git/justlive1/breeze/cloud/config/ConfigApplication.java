package net.oschina.git.justlive1.breeze.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 程序入口
 * 
 * @author wubo
 *
 */
@EnableAutoConfiguration(
    exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication(scanBasePackages = "net.oschina.git.justlive1.breeze")
public class ConfigApplication {

  public static void main(String[] args) {

    SpringApplication.run(ConfigApplication.class, args);
  }

}
