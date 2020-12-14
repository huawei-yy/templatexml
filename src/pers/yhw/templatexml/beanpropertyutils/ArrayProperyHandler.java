package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Type;

public class ArrayProperyHandler implements PropertyHandler {

	@Override
	public PropertyInfo getProperty(PropertyInfo upPropertyInfo, String subPath) {

		PropertyInfo propertyInfo = new PropertyInfo();
		Object[] array = (Object[]) upPropertyInfo.getValue();
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
			type = upPropertyInfo.getType().getComponentType();
		}
		propertyInfo.setGenericTypes(genericTypes);
		propertyInfo.setType(type);
		propertyInfo.setValue(value);
		return propertyInfo;

	}

	@Override
	public void setProperty(PropertyInfo upPropertyInfo, String subPath, Object value) {
		// TODO Auto-generated method stub

	}

}
