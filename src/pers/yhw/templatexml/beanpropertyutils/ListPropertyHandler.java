package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListPropertyHandler implements PropertyHandler {
	ListPropertyHandler() {

	}

	@Override
	public PropertyInfo getProperty(PropertyInfo upperPropertyInfo, String subPath) {
		PropertyInfo propertyInfo = new PropertyInfo();
		List list = (List) upperPropertyInfo.getValue();
		Type[] upGenericTypes = upperPropertyInfo.getGenericTypes();
		Object value = null;
		if (PathParser.isIndexed(subPath)) {
			int intdex = PathParser.parseIndexedPath(subPath);
			if (intdex < list.size()) {
				value = list.get(intdex);
			}
		} else {
			throw new IllegalArgumentException(subPath + "is illegalArgument");
		}
		Type[] genericTypes = null;
		Class type = null;
		if (value != null) {
			type = value.getClass();
		} else {
			if (upGenericTypes != null && upGenericTypes.length >= 1) {
				if (upGenericTypes[0] instanceof ParameterizedType) {
					type = (Class) ((ParameterizedType) upGenericTypes[0]).getRawType();
					genericTypes = ((ParameterizedType) upGenericTypes[0]).getActualTypeArguments();
				} else if (upGenericTypes[0] instanceof Class) {
					type = (Class) (upGenericTypes[0]);
				}
			}
		}
		propertyInfo.setGenericTypes(genericTypes);
		propertyInfo.setType(type);
		propertyInfo.setValue(value);
		return propertyInfo;
	}

	@Override
	public void setProperty(PropertyInfo upperPropertyInfo, String subPath, Object value) {
		List list = (List) upperPropertyInfo.getValue();
		Class upperType = upperPropertyInfo.getType();
		if (PathParser.isIndexed(subPath)) {
		} else {
			throw new IllegalArgumentException(subPath + "is illegalArgument");
		}
		int index = PathParser.parseIndexedPath(subPath);
		while (index > list.size() - 1) {
			list.add(null);
		}
		list.set(index, value);
	}

}
