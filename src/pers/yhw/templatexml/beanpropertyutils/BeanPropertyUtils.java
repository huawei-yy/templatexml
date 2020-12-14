package pers.yhw.templatexml.beanpropertyutils;

import java.lang.reflect.Type;

public class BeanPropertyUtils {
	public static Object getProperty(Object object, String path) {
		return getProperty(object, path, null);
	}

	public static Object getProperty(Object object, String path, Type... genericTypes) {
		PathParser pathParser = new PathParser(path);
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(object.getClass());
		propertyInfo.setValue(object);
		propertyInfo.setGenericTypes(genericTypes);
		while (pathParser.hasNext()) {
			String subPath = pathParser.next();
			PropertyHandler propertyHandler = PropertyHandlerManager.getPropertyHandler(propertyInfo.getType());
			propertyInfo = propertyHandler.getProperty(propertyInfo, subPath);
			if (propertyInfo.getValue() == null) {
				return null;
			}
		}
		return propertyInfo.getValue();
	}

}
