package pers.yhw.templatexml.beanpropertyutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PrimitiveType {
	static final Set<Class> primitiveTypes = Collections.synchronizedSet(new HashSet<Class>());
	static {
		addPrimitiveType(Boolean.class);
		addPrimitiveType(Character.class);
		addPrimitiveType(Byte.class);
		addPrimitiveType(Short.class);
		addPrimitiveType(Integer.class);
		addPrimitiveType(Long.class);
		addPrimitiveType(Float.class);
		addPrimitiveType(Double.class);
		addPrimitiveType(BigInteger.class);
		addPrimitiveType(BigDecimal.class);
		addPrimitiveType(String.class);
		addPrimitiveType(java.util.Date.class);
		addPrimitiveType(java.sql.Date.class);
		addPrimitiveType(java.sql.Time.class);
		addPrimitiveType(java.sql.Timestamp.class);
	}

	public static void addPrimitiveType(Class clazz) {
		primitiveTypes.add(clazz);
	}

	public static boolean isPrimitive(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.isEnum() || primitiveTypes.contains(clazz);
	}
}
