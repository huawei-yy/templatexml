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

	public static Object getPropertyOrDefult(Object object, String path) {
		PathParser pathParser = new PathParser(path);
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(object.getClass());
		propertyInfo.setValue(object);
		while (pathParser.hasNext()) {
			String subPath = pathParser.next();
			PropertyHandler propertyHandler = PropertyHandlerManager.getPropertyHandler(propertyInfo.getType());
			PropertyInfo tempropertyInfo = propertyHandler.getProperty(propertyInfo, subPath);
			if (tempropertyInfo.getValue() == null) {
				Object defultValue = NewInstanceUtils.newInstance(tempropertyInfo.getType());
				propertyHandler.setProperty(propertyInfo, subPath, defultValue);
				tempropertyInfo.setValue(defultValue);
			}
			propertyInfo = tempropertyInfo;
		}
		return propertyInfo.getValue();
	}

	public static void setProperty(Object object, String path, Object value) {
		PathParser pathParser = new PathParser(path);
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(object.getClass());
		propertyInfo.setValue(object);
		while (pathParser.hasNext()) {
			String subPath = pathParser.next();
			PropertyHandler propertyHandler = PropertyHandlerManager.getPropertyHandler(propertyInfo.getType());
			PropertyInfo tempropertyInfo = propertyHandler.getProperty(propertyInfo, subPath);
			if (pathParser.hasNext()) {
				if (tempropertyInfo.getValue() == null) {
					Object defultValue = NewInstanceUtils.newInstance(tempropertyInfo.getType());
					propertyHandler.setProperty(propertyInfo, subPath, defultValue);
				}
				propertyInfo = tempropertyInfo;
			} else {
				value = TypeConverUtils.cast(value, tempropertyInfo.getType());
				propertyHandler.setProperty(propertyInfo, subPath, value);
			}
		}
	}

}
