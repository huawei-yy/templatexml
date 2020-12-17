package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

public class NewInstanceUtils {
	static <T> T newInstance(Class<T> clazz) {
		T value = null;
		if (clazz.isArray()) {
			value = (T) Array.newInstance(clazz.getComponentType(), 0);
		} else if (clazz.isPrimitive()) {
			if (clazz == int.class) {
				value = (T) Integer.valueOf("0");
			} else if (clazz == short.class) {
				value = (T) Short.valueOf("0");
			} else if (clazz == float.class) {
				value = (T) Float.valueOf("0");
			} else if (clazz == double.class) {
				value = (T) Double.valueOf("0");
			} else if (clazz == byte.class) {
				value = (T) Byte.valueOf("0");
			} else if (clazz == char.class) {
				value = (T) new Character('\u0000');
			} else if (clazz == long.class) {
				value = (T) Long.valueOf("0");
			} else if (clazz == boolean.class) {
				value = (T) Boolean.FALSE;
			}
		} else if (clazz.isEnum()) {
			Object[] enumConstants = clazz.getEnumConstants();
			if (enumConstants.length > 0) {
				value = (T) enumConstants[0];
			} else {
				value = null;
			}
		} else if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
			if (clazz == Map.class) {
				value = (T) new HashMap();
			} else if (clazz == List.class) {
				value = (T) new ArrayList();
			} else {
				throw new RuntimeException(new InstantiationException(clazz + " cannot  instantiation"));
			}
		} else {
			try {
				value = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return value;

	}
}
