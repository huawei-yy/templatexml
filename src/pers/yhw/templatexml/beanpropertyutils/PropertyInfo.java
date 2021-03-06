package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Type;

public class PropertyInfo {
	private Class type;
	private Object value;
	private Type[] genericTypes;

	PropertyInfo() {

	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Type[] getGenericTypes() {
		return genericTypes;
	}

	public void setGenericTypes(Type[] genericTypes) {
		this.genericTypes = genericTypes;
	}

}
