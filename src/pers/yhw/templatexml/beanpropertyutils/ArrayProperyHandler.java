package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Type;

public class ArrayProperyHandler implements PropertyHandler {

	@Override
	public PropertyInfo getProperty(PropertyInfo upperPropertyInfo, String subPath) {
		PropertyInfo propertyInfo = new PropertyInfo();
		Object[] array = (Object[]) upperPropertyInfo.getValue();
		Object value = null;
		if (PathParser.isIndexed(subPath)) {
			value = array[PathParser.parseIndexedPath(subPath)];
		} else {
			throw new IllegalArgumentException(subPath + "is illegalArgument");
		}
		Type[] genericTypes = null;
		Class type = null;
		if (value != null) {
			type = value.getClass();
		} else {
			type = upperPropertyInfo.getType().getComponentType();
		}
		propertyInfo.setGenericTypes(genericTypes);
		propertyInfo.setType(type);
		propertyInfo.setValue(value);
		return propertyInfo;

	}

	@Override
	public void setProperty(PropertyInfo upperPropertyInfo, String subPath, Object value) {
		PropertyInfo propertyInfo = new PropertyInfo();
		int index = PathParser.parseIndexedPath(subPath);
		Object[] array = (Object[]) upperPropertyInfo.getValue();
		Class type = upperPropertyInfo.getType().getComponentType();
		array[index] = value;
	}

}
