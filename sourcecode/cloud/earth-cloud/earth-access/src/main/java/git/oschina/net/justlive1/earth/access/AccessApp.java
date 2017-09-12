package git.oschina.net.justlive1.earth.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import git.oschina.net.justlive1.earth.access.config.CommonProperties;

@SpringBootApplication
@EnableConfigurationProperties(CommonProperties.class)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 100, redisNamespace = "earth")
public class AccessApp {

	public static void main(String[] args) {
		SpringApplication.run(AccessApp.class, args);
	}

}
