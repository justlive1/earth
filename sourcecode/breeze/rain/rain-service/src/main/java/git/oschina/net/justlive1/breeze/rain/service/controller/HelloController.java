package git.oschina.net.justlive1.breeze.rain.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

		List<String> res = helloService.hello();

		return JsonResponse.success(res);
	}

	@PostMapping("/world")
	public JsonResponse<List<String>> world(@RequestBody List<String> list) {

		List<String> res = helloService.world(list);

		return JsonResponse.success(res);
	}
}
