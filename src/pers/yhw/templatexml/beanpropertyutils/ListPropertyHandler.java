package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListPropertyHandler implements PropertyHandler {

	@Override
	public PropertyInfo getProperty(PropertyInfo upPropertyInfo, String subPath) {
		PropertyInfo propertyInfo = new PropertyInfo();
		List list = (List) upPropertyInfo.getValue();
		Type[] upGenericTypes = upPropertyInfo.getGenericTypes();
		Object value = null;
		if (PathParser.isIndexed(subPath)) {
			value = list.get(PathParser.parseIndexedPath(subPath));
		} else {
			throw new IllegalArgumentException(subPath + "is illegalArgument");
		}
		Type[] genericTypes = null;
		Class type = null;
		if (value != null) {
			type = value.getClass();
		} else {
			if (upGenericTypes != null && upGenericTypes.length >= 1) {
				if (upGenericTypes[1] instanceof ParameterizedType) {
					type = (Class) ((ParameterizedType) upGenericTypes[0]).getRawType();
					genericTypes = ((ParameterizedType) upGenericTypes[0]).getActualTypeArguments();
				}
			}
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
