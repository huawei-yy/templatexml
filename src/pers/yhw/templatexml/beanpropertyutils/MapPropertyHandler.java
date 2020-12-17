package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MapPropertyHandler implements PropertyHandler {
	MapPropertyHandler() {

	}

	@Override
	public PropertyInfo getProperty(PropertyInfo upperPropertyInfo, String subPath) {
		PropertyInfo propertyInfo = new PropertyInfo();
		Map map = (Map) upperPropertyInfo.getValue();
		Type[] upGenericTypes = upperPropertyInfo.getGenericTypes();
		Object value = map.get(subPath);
		Type[] genericTypes = null;
		Class type = null;
		if (value != null) {
			type = value.getClass();
		} else {
			if (upGenericTypes != null && upGenericTypes.length >= 2) {
				if (upGenericTypes[1] instanceof ParameterizedType) {
					type = (Class) ((ParameterizedType) upGenericTypes[1]).getRawType();
					genericTypes = ((ParameterizedType) upGenericTypes[1]).getActualTypeArguments();
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
		Map map = (Map) upperPropertyInfo.getValue();
		map.put(subPath, value);
	}

}
