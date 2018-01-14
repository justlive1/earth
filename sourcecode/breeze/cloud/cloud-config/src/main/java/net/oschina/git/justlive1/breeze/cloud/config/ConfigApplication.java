package net.oschina.git.justlive1.breeze.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

/**
 * 程序入口
 * 
 * @author wubo
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigApplication {

	public static void main(String[] args) {

		SpringApplication.run(ConfigApplication.class, args);
	}

}
