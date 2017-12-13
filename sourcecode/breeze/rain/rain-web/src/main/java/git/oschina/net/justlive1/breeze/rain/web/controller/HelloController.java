package git.oschina.net.justlive1.breeze.rain.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import git.oschina.net.justlive1.breeze.rain.api.domian.Hello;
import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;
import git.oschina.net.justlive1.breeze.snow.common.web.domain.Response;

@RestController
@RequestMapping("/test")
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public Response<List<String>> hello() {

		List<String> list = helloService.hello();

		return Response.success(list);
	}

	@GetMapping("/world")
	public Response<List<String>> world() {

		List<String> list = Arrays.asList("hello", "world");

		list = helloService.world(list);

		return Response.success(list);
	}

	@GetMapping("/helloworld")
	public Response<List<String>> helloworld() {

		Hello hello = new Hello();
		hello.setMsg("hello world");

		List<String> list = helloService.helloworld(hello);

		return Response.success(list);
	}
}
