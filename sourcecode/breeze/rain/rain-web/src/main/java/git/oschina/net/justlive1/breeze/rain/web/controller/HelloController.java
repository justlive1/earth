package git.oschina.net.justlive1.breeze.rain.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@Controller
@RequestMapping("/test")
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping
	public String hello() {

		return helloService.say();
	}
}
