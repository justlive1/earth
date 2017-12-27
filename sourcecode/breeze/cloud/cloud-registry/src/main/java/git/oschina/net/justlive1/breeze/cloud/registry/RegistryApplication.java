package git.oschina.net.justlive1.breeze.cloud.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 程序入口
 * 
 * @author wubo
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {

	public static void main(String[] args) {

		SpringApplication.run(RegistryApplication.class, args);
	}
}
