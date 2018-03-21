package justlive.earth.breeze.cloud.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 程序入口
 * 
 * @author wubo
 *
 */
@EnableAutoConfiguration(
    exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableAdminServer
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackages = "net.oschina.git.justlive1.breeze")
public class SpringBootAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAdminApplication.class, args);
  }
}
