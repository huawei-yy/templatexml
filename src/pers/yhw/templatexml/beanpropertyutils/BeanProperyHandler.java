package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public class BeanProperyHandler implements PropertyHandler {
	private static ConcurrentHashMap<Class, ConcurrentHashMap<String, Method>> class_MethodMap = new ConcurrentHashMap<Class, ConcurrentHashMap<String, Method>>();
	private static ConcurrentHashMap<Class, ConcurrentHashMap<String, Field>> class_FiledMap = new ConcurrentHashMap<Class, ConcurrentHashMap<String, Field>>();

	BeanProperyHandler() {

	}

	@Override
	public PropertyInfo getProperty(PropertyInfo upperPropertyInfo, String subPath) {
		PropertyInfo propertyInfo = new PropertyInfo();
		Object object = upperPropertyInfo.getValue();
		Type[] upGenericTypes = upperPropertyInfo.getGenericTypes();
		Object value = null;
		Type[] genericTypes = null;
		Class type = null;
		Class upType = upperPropertyInfo.getType();
		Field field = getDeclaredField(upType, subPath);
		Method getMethod = null;
		String name = (new StringBuilder()).append(Character.toUpperCase(subPath.charAt(0)))
				.append(subPath.substring(1)).toString();
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			try {
				getMethod = getDeclaredMethod(upType, "is" + name);
			} catch (Exception e) {
				getMethod = getDeclaredMethod(upType, "get" + name);
			}
		} else {
			getMethod = getDeclaredMethod(upType, "get" + name);
		}
		if (getMethod != null) {
			try {
				value = getMethod.invoke(object);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		if (value != null) {
			type = value.getClass();
		} else {
			type = field.getType();
		}
		Type genericType = field.getGenericType();
		if (genericType != null && genericType instanceof ParameterizedType) {
			genericTypes = ((ParameterizedType) genericType).getActualTypeArguments();
		}
		propertyInfo.setGenericTypes(genericTypes);
		propertyInfo.setType(type);
		propertyInfo.setValue(value);
		return propertyInfo;
	}

	@Override
	public void setProperty(PropertyInfo upperPropertyInfo, String subPath, Object value) {
		Object object = upperPropertyInfo.getValue();
		Class upperType = upperPropertyInfo.getType();
		Method setMethod = null;
		String name = (new StringBuilder()).append(Character.toUpperCase(subPath.charAt(0)))
				.append(subPath.substring(1)).toString();
		setMethod = getDeclaredMethod(upperType, "set" + name);
		if (setMethod != null) {
			try {
				setMethod.invoke(object, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 根据域名获得域 （包含父类）
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 * @throws NoSuchFieldException
	 */
	Field getDeclaredField(Class clazz, String name) {
		Field field = null;
		ConcurrentHashMap<String, Field> filedMap = class_FiledMap.get(clazz);
		if (filedMap == null) {
			filedMap = new ConcurrentHashMap<String, Field>();
			class_FiledMap.put(clazz, filedMap);
		} else {
			field = filedMap.get(name);
		}
		if (field == null) {
			try {
				field = clazz.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				Class superclass = clazz.getSuperclass();
				if (superclass == Object.class) {
					throw new RuntimeException(new NoSuchFieldException(name));
				} else {
					field = getDeclaredField(superclass, name);
				}
			}
			filedMap.put(name, field);
		}
		return field;
	}

	/**
	 * 获得方法（包含父类）
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	static Method getDeclaredMethod(Class clazz, String methodName) {
		Method method = null;
		ConcurrentHashMap<String, Method> methodMap = class_MethodMap.get(clazz);
		if (methodMap == null) {
			methodMap = new ConcurrentHashMap<String, Method>();
			class_MethodMap.put(clazz, methodMap);
		} else {
			method = methodMap.get(methodName);
		}
		if (method == null) {
			for (Method methodtemp : clazz.getDeclaredMethods()) {
				String methodtempName = methodtemp.getName();
				if (methodName.equals(methodtempName)) {
					method = methodtemp;
					break;
				}
			}
			if (method == null) {
				Class superclass = clazz.getSuperclass();
				if (superclass == Object.class) {
					throw new RuntimeException(new NoSuchMethodException(clazz.getName() + "." + methodName));
				} else {
					method = getDeclaredMethod(superclass, methodName);
				}
			}
			methodMap.put(methodName, method);
		}
		return method;
	}

}
