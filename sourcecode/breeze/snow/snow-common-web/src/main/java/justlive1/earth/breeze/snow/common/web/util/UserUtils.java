package justlive1.earth.breeze.snow.common.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import com.google.common.collect.Maps;
import justlive1.earth.breeze.snow.common.base.exception.Exceptions;
import justlive1.earth.breeze.snow.common.base.util.ReflectUtils;

/**
 * 用户相关工具类
 * 
 * @author wubo
 *
 */
public class UserUtils {

  private static final String CAS_USERUTILS_CLASS =
      "git.oschina.net.justlive1.breeze.storm.cas.client.util.CasUserUtils";
  private static final String SECURITY_USERUTILS_CLASS =
      "git.oschina.net.justlive1.breeze.storm.cas.client.security.util.SercurityUserUtils";

  private static final Map<String, Method> CACHE_MAP = Maps.newHashMap();

  private UserUtils() {}

  /**
   * 获取当前登陆用户名
   * 
   * @return
   */
  public static String loginUserName() {

    return invoke();
  }

  private static String invoke() {

    String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    Method method = CACHE_MAP.get(methodName);
    if (method == null) {

      Class<?> clazz = null;
      if (ClassUtils.isPresent(CAS_USERUTILS_CLASS, UserUtils.class.getClassLoader())) {
        clazz = ReflectUtils.forName(CAS_USERUTILS_CLASS);
      } else if (ClassUtils.isPresent(SECURITY_USERUTILS_CLASS, UserUtils.class.getClassLoader())) {
        clazz = ReflectUtils.forName(SECURITY_USERUTILS_CLASS);
      }

      if (clazz == null) {
        return null;
      }

      method = ReflectionUtils.findMethod(clazz, methodName);
      CACHE_MAP.put(methodName, method);
    }
    return invoke(method, null);
  }

  @SuppressWarnings("unchecked")
  private static <T> T invoke(Method method, Object obj, Object... args) {
    try {
      return (T) method.invoke(obj, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw Exceptions.wrap(e);
    }
  }

}
