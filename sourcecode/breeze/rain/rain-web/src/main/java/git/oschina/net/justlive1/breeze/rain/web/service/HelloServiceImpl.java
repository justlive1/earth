package git.oschina.net.justlive1.breeze.rain.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import git.oschina.net.justlive1.breeze.rain.api.domian.Hello;
import git.oschina.net.justlive1.breeze.rain.api.service.HelloService;
import git.oschina.net.justlive1.breeze.snow.common.web.base.BaseService;

@Service
public class HelloServiceImpl extends BaseService implements HelloService {

	@Value("${rain.service.url}")
	String serviceUrl;

	@Autowired
	@Override
	protected void setTemplate(RestTemplate template) {
		super.setTemplate(template);
	}

	@Override
	public List<String> hello() {

		return this.getForObject(serviceUrl + "/test/hello");
	}

	@Override
	public List<String> world(List<String> world) {

		int i = 0;
		i = 10 / i;

		return this.postJsonForObject(serviceUrl + "/test/world", world);
	}

	@Override
	public List<String> helloworld(Hello hello) {

		return this.formSubmitForObject(serviceUrl + "/test/helloworld", hello);
	}
}
