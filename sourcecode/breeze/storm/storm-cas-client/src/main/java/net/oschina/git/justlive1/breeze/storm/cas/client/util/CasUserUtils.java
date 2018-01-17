package net.oschina.git.justlive1.breeze.storm.cas.client.util;

import org.jasig.cas.client.util.AssertionHolder;

/**
 * cas对应的用户工具类
 *
 * @author wubo
 *
 */
public class CasUserUtils {

	/**
	 * 获取当前登陆用户名
	 * 
	 * @return
	 */
	public static String loginUserName() {

		return AssertionHolder.getAssertion().getPrincipal().getName();
	}
}