package git.oschina.net.justlive1.breeze.rain.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Autowired
	RestTemplate template;

	@Value("${rain.service.url}")
	String serviceUrl;

	@Override
	public String say() {
		return template.getForObject(serviceUrl + "/test/hello", String.class);
	}
}
