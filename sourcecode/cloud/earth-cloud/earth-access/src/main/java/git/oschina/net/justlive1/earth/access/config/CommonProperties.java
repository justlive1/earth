package git.oschina.net.justlive1.earth.access.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "earth")
@Data
public class CommonProperties {

	/**
	 * 登录地址
	 */
	private String loginUrl;

	/**
	 * 登录成功跳转地址
	 */
	private String successUrl;

	/**
	 * 不走权限的地址
	 */
	private String[] antMatchers;

	/**
	 * 默认向导用户名
	 */
	private String guideName;

	/**
	 * 默认向导密码
	 */
	private String guidePassword;

	/**
	 * 默认向导角色
	 */
	private String guideRole;
}
