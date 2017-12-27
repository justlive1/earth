package git.oschina.net.justlive1.breeze.snow.common.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * 反射相关工具类
 * 
 * @author wubo
 *
 */
public class ReflectUtils {

	/**
	 * 获取class
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> forName(String className) {
		Class<?> clazz = null;
		ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			if (threadContextClassLoader != null) {
				try {
					clazz = threadContextClassLoader.loadClass(className);
				} catch (ClassNotFoundException e) {// NOSONOR
				}
			}

			if (clazz == null) {
				clazz = ReflectUtils.class.getClassLoader().loadClass(className);
			}
		} catch (ClassNotFoundException e) {// NOSONOR
		}

		if (clazz == null) {
			throw new RuntimeException("Failed to load driver class " + className
					+ " in either of DataSourceUtils class loader or Thread context classloader");
		}

		return clazz;
	}

	/**
	 * 实例化对象
	 * 
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromName(String className) {
		Class<?> clazz = forName(className);

		try {
			return (T) newInstance(clazz);
		} catch (Exception e) {
			throw new RuntimeException("Failed to instantiate class " + className, e);
		}

	}

	/**
	 * 获取当前Class类实例中所有的Field对象（包括父类的Field）
	 * 
	 * @param clazz
	 *            Class类实例
	 * @return 对象类型数组
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return fields;
		}
		Field[] superFields = getAllDeclaredFields(superClass);
		Field[] destFields = new Field[fields.length + superFields.length];
		System.arraycopy(fields, 0, destFields, 0, fields.length);
		System.arraycopy(superFields, 0, destFields, fields.length, superFields.length);

		return destFields;
	}

	/**
	 * 根据属性名，在当前Class类实例的所有Field对象中（包括父类的Field）检索对应的属性值
	 * 
	 * @param clazz
	 *            Class类实例
	 * @param propertyName
	 *            属性名
	 * @return Field 对象
	 */
	public static Field getDeclaredField(Class<?> clazz, String propertyName) {
		Field[] fields = getAllDeclaredFields(clazz);
		for (Field field : fields) {
			if (field.getName().equals(propertyName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 根据属性名来获取属性的值
	 * 
	 * @param object
	 *            需要得到属性值的对象
	 * @param propertyName
	 *            属性名
	 * @return Object 属性值
	 */
	public static Object getValue(Object object, String propertyName) {
		Class<?> clazz = object.getClass();
		Field field = getDeclaredField(clazz, propertyName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把传入对象的非空对象的值赋给持久化对象
	 * 
	 * @param persistentObject
	 *            持久化对象
	 * @param newObject
	 *            传入的对象
	 * @throws IllegalAccessException
	 *             访问非法异常
	 * @throws IllegalArgumentException
	 *             非法逻辑异常
	 */
	public static void replaceNullProperty(Object persistentObject, Object newObject)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = persistentObject.getClass();
		Field[] fields = getAllDeclaredFields(clazz);
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			Object fieldContent = fields[i].get(newObject);
			if (fieldContent != null && !Modifier.isFinal(fields[i].getModifiers())) {
				fields[i].set(persistentObject, fieldContent);
			}
		}
	}

	/**
	 * class中是否存在指定的方法
	 * 
	 * @param clazz
	 *            CLASS类型
	 * @param methodName
	 *            方法名
	 * @return boolean 否存在指定的方法中
	 */
	public static boolean isContainMethod(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * clone一个Object使用序列号的形式
	 * 
	 * @param src
	 *            需要序列化的对象
	 * @return
	 */
	public static Object byteClone(Object src) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(src);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone = in.readObject();
			in.close();
			return clone;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 实例化一个类型为clazz的对象。
	 * 
	 * @param <T>
	 *            实例化对象类型
	 * @param clazz
	 *            实例化类型
	 * @return 实例化的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> clazz) {
		if (clazz == Integer.class) {
			return (T) new Integer(0);
		}
		if (clazz == String.class) {
			return (T) new String();
		}
		if (clazz == Long.class) {
			return (T) new Long(0L);
		}
		if (clazz == Short.class) {
			return (T) new Short((short) 0);
		}
		if (clazz == Byte.class) {
			return (T) new Byte((byte) 0);
		}
		if (clazz == Float.class) {
			return (T) new Float(0.0);
		}
		if (clazz == Double.class) {
			return (T) new Double(0.0);
		}
		if (clazz == Boolean.class) {
			return (T) new Boolean(false);
		}
		if (clazz == BigDecimal.class) {
			return (T) new BigDecimal(0);
		}
		if (clazz == java.sql.Date.class) {
			return (T) new java.sql.Date(System.currentTimeMillis());
		}
		if (clazz == java.sql.Timestamp.class) {
			return (T) new java.sql.Timestamp(System.currentTimeMillis());
		}
		if (clazz == java.util.Map.class) {
			return (T) new HashMap<>(16);
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Can NOT instance new object for class [" + clazz + "]", e);
		}
	}

	/**
	 * clone一个新的对象。
	 * 
	 * @param <T>
	 *            需要创建的对象的类型
	 * @param ex
	 *            实例
	 * @return 返回clone的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T ex) {
		Class<T> clazz = (Class<T>) ex.getClass();
		if (clazz == Integer.class) {
			return (T) new Integer((Integer) ex);
		}
		if (clazz == String.class) {
			return (T) new String((String) ex);
		}
		if (clazz == Long.class) {
			return (T) new Long((Long) ex);
		}
		if (clazz == Short.class) {
			return (T) new Short((Short) ex);
		}
		if (clazz == Byte.class) {
			return (T) new Byte((Byte) ex);
		}
		if (clazz == Float.class) {
			return (T) new Float((Float) ex);
		}
		if (clazz == Double.class) {
			return (T) new Double((Double) ex);
		}
		if (clazz == Boolean.class) {
			return (T) new Boolean((Boolean) ex);
		}
		return (T) byteClone(ex);

	}

	public static Field[] getInheritanceDeclaredFields(Class<?> clazz) {
		Collection<Field> fds = new ArrayList<Field>();
		while (true) {
			if (clazz.isPrimitive() || clazz.equals(Object.class)) {
				break;
			}
			Collections.addAll(fds, clazz.getDeclaredFields());
			clazz = clazz.getSuperclass();
		}
		Field[] ret = new Field[fds.size()];
		fds.toArray(ret);
		fds.clear();
		return ret;
	}
}
