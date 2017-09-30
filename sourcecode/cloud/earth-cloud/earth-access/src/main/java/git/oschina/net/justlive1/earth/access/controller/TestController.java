package git.oschina.net.justlive1.earth.access.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/hello")
	public Map<String, String> hello(HttpSession session, HttpServletRequest request) {

		Map<String, String> map = new HashMap<>();

		map.put("sessionId", session.getId());
		
		String path = request.getServletPath();
		System.out.println(path);

		return map;
	}

	@RequestMapping("/put")
	public Map<String, String> put(String key, String value, HttpSession session) {

		Map<String, String> map = new HashMap<>();

		map.put("sessionId", session.getId());

		session.setAttribute(key, value);

		return map;
	}

	@RequestMapping("/get")
	public Map<String, String> get(String key, HttpSession session) {

		Map<String, String> map = new HashMap<>();

		map.put("sessionId", session.getId());

		String value = (String) session.getAttribute(key);

		map.put(key, value);

		return map;
	}
	
	
}
