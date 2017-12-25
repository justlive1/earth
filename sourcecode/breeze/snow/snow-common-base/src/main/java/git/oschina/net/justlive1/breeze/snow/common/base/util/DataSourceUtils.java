package git.oschina.net.justlive1.breeze.snow.common.base.util;

import java.sql.Driver;

/**
 * 数据源工具类
 * 
 * @author wubo
 *
 */
public class DataSourceUtils {

	/**
	 * 实例化Driver
	 * 
	 * @param driverClassName
	 * @return
	 */
	public static Driver fromName(String driverClassName) {

		Class<?> driverClass = null;
		ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			if (threadContextClassLoader != null) {
				try {
					driverClass = threadContextClassLoader.loadClass(driverClassName);
				} catch (ClassNotFoundException e) {// NOSONOR
				}
			}

			if (driverClass == null) {
				driverClass = DataSourceUtils.class.getClassLoader().loadClass(driverClassName);
			}
		} catch (ClassNotFoundException e) {// NOSONOR
		}

		if (driverClass == null) {
			throw new RuntimeException("Failed to load driver class " + driverClassName
					+ " in either of DataSourceUtils class loader or Thread context classloader");
		}

		try {
			return (Driver) driverClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to instantiate class " + driverClassName, e);
		}

	}
}
