package git.oschina.net.justlive1.breeze.rain.service.service;

import org.springframework.stereotype.Service;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String say() {
		return "hello world";
	}

}
