package git.oschina.net.justlive1.breeze.rain.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;
import git.oschina.net.justlive1.breeze.snow.common.web.domain.JsonResponse;

@RestController
@RequestMapping("/test")
public class HelloController {

	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public JsonResponse<List<String>> hello() {

		List<String> list = helloService.hello();

		return JsonResponse.success(list);
	}

	@GetMapping("/world")
	public JsonResponse<List<String>> world() {

		List<String> list = Arrays.asList("hello", "world");

		list = helloService.world(list);

		return JsonResponse.success(list);
	}
}
