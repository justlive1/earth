package git.oschina.net.justlive1.breeze.snow.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import git.oschina.net.justlive1.breeze.snow.common.base.constant.BaseConstants;

/**
 * 公共web请求
 * 
 * @author wubo
 *
 */
@Controller
@RequestMapping("/common")
public class CommonWebController {

	@GetMapping("/checkWeb")
	@ResponseBody
	public String checkWeb() {
		return "checkWeb:" + BaseConstants.CHECK_WEB_KEY;
	}

}
