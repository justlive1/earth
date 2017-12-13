package git.oschina.net.justlive1.breeze.rain.api.service;

import java.util.List;

import git.oschina.net.justlive1.breeze.rain.api.domian.Hello;

public interface HelloService {

	List<String> hello();

	List<String> world(List<String> world);

	List<String> helloworld(Hello hello);
}
