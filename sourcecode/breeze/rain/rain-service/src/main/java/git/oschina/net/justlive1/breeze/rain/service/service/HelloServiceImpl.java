package git.oschina.net.justlive1.breeze.rain.service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public List<String> hello() {

		return Arrays.asList("hello", "world");
	}

	@Override
	public List<String> world(List<String> world) {

		return world;
	}
}
