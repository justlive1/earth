package git.oschina.net.justlive1.breeze.snow.common.base.util;

import static git.oschina.net.justlive1.breeze.snow.common.base.constant.BaseConstants.*;
import static git.oschina.net.justlive1.breeze.snow.common.base.exception.Exceptions.errorMessage;
import static git.oschina.net.justlive1.breeze.snow.common.base.exception.Exceptions.fail;

import java.util.regex.Pattern;

import git.oschina.net.justlive1.breeze.snow.common.base.exception.ErrorCode;
import git.oschina.net.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 校验工具类
 * 
 * @author wubo
 *
 */
public class Checks {

	public static final String MODULE_VALID = "VALID";
	public static final String NULL_VALUE = "-00001";
	public static final String INVAID_EMAIL = "-00002";

	/**
	 * 非空检查
	 */
	public static void notNull(Object obj) {
		notNull(obj, "can not be null");
	}

	public static void notNull(Object obj, String msg) {
		notNull(obj, errorMessage(MODULE_VALID, NULL_VALUE, msg));
	}

	public static void notNull(Object obj, ErrorCode errCode, Object... params) {
		if (obj == null) {
			throw fail(errCode, params);
		}
	}

	/**
	 * 验证Email
	 * 
	 * @param email
	 *            email地址
	 */
	public static void email(String email) {
		email(email, "email is invalid");
	}

	public static void email(String email, String msg) {
		email(email, errorMessage(MODULE_VALID, INVAID_EMAIL, msg));
	}

	public static void email(String email, ErrorCode errCode, Object... params) {
		if (!Pattern.matches(REGEX_EMAIL, email)) {
			throw Exceptions.fail(errCode, params);
		}
	}

}
