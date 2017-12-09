package git.oschina.net.justlive1.breeze.rain.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@RestController
@RequestMapping("/test")
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public String hello() {

		return helloService.say();
	}
}
