package git.oschina.net.justlive1.breeze.snow.common.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import git.oschina.net.justlive1.breeze.snow.common.base.util.ReflectUtils;

/**
 * 用户相关工具类
 * 
 * @author wubo
 *
 */
public class UserUtils {

	private static final String CAS_USERUTILS_CLASS = "git.oschina.net.justlive1.breeze.storm.cas.client.util.CasUserUtils";
	private static final String SECURITY_USERUTILS_CLASS = "git.oschina.net.justlive1.breeze.storm.cas.client.security.util.SercurityUserUtils";

	private static final Method LOGIN_USERNAME_METHOD;
	private static final String LOGIN_USERNAME_METHOD_NAME = "loginUserName";

	static {

		Class<?> clazz = null;
		if (ClassUtils.isPresent(CAS_USERUTILS_CLASS, UserUtils.class.getClassLoader())) {
			clazz = ReflectUtils.forName(CAS_USERUTILS_CLASS);
		} else if (ClassUtils.isPresent(SECURITY_USERUTILS_CLASS, UserUtils.class.getClassLoader())) {
			clazz = ReflectUtils.forName(SECURITY_USERUTILS_CLASS);
		}
		// else NullPointerException
		LOGIN_USERNAME_METHOD = ReflectionUtils.findMethod(clazz, LOGIN_USERNAME_METHOD_NAME);
	}

	/**
	 * 获取当前登陆用户名
	 * 
	 * @return
	 */
	public static String loginUserName() {

		return invoke(LOGIN_USERNAME_METHOD, null);
	}

	@SuppressWarnings("unchecked")
	private static <T> T invoke(Method method, Object obj, Object... args) {
		try {
			return (T) method.invoke(obj, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
