package net.oschina.git.justlive1.breeze.snow.common.base.constant;

/**
 * 基本常量
 * 
 * @author wubo
 *
 */
public interface BaseConstants {

	/**
	 * 验证web启动key
	 */
	String CHECK_WEB_KEY = "4c30c1be81144134bb9dc766ddf7f1b6";

	/**
	 * 返回实体code属性字段
	 */
	String RESP_CODE_FIELD = "code";

	/**
	 * 返回实体message属性字段
	 */
	String RESP_MESSAGE_FIELD = "message";

	/**
	 * Email校验正则
	 */
	String REGEX_EMAIL = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";

	/**
	 * 身份证校验正则
	 */
	String REGEX_IDCARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
	
	/**
	 * 二代身份证校验正则
	 */
	String REGEX_IDCARD2ND = "";
}