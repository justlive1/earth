package git.oschina.net.justlive1.breeze.rain.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Autowired
	RestTemplate template;

	@Override
	public String say() {
		return template.getForObject("http://127.0.0.1:8080/rain-service/test/hello", String.class);
	}
}
