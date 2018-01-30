package net.oschina.git.justlive1.breeze.rain.service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.oschina.git.justlive1.breeze.rain.api.domian.Hello;
import net.oschina.git.justlive1.breeze.rain.api.service.HelloService;

/**
 * demo
 * 
 * @author wubo
 *
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public List<String> hello() {

        int i = 0;
        i = 10 / i;

        return Arrays.asList("hello", "world");
    }

    @Override
    public List<String> world(List<String> world) {

        return world;
    }

    @Override
    public List<String> helloworld(Hello hello) {

        return Arrays.asList(StringUtils.split(hello.getMsg(), " "));
    }
}
