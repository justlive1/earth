package net.oschina.git.justlive1.breeze.cloud.registry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 程序入口
 * 
 * @author wubo
 *
 */
@SpringBootApplication(scanBasePackages = "net.oschina.git.justlive1.breeze", exclude = {
        SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EnableEurekaServer
@Controller
@RequestMapping
public class RegistryApplication {

    public static void main(String[] args) {

        SpringApplication.run(RegistryApplication.class, args);
    }

    @Value("${earth.registry.path:/eureka}")
    String registryPath;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("forward:/");
    }

    @RequestMapping("${earth.registry.path}/**")
    public ModelAndView registry(HttpServletRequest request) {
        return new ModelAndView("forward:/eureka" + request.getRequestURI().replaceFirst(registryPath, ""));
    }
}
