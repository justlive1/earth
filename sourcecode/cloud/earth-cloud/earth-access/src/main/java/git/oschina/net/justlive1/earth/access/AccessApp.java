package git.oschina.net.justlive1.earth.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 100, redisNamespace = "earth")
public class AccessApp {

	public static void main(String[] args) {
		SpringApplication.run(AccessApp.class, args);
	}

}
