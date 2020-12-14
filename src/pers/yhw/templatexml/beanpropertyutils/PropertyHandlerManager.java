package pers.yhw.templatexml.beanpropertyutils;

import java.util.List;
import java.util.Map;

public class PropertyHandlerManager {
	static BeanProperyHandler beanProperyHandler = new BeanProperyHandler();
	static ArrayProperyHandler arrayProperyHandler = new ArrayProperyHandler();
	static MapPropertyHandler mapPropertyHandler = new MapPropertyHandler();
	static ListPropertyHandler listPropertyHandler = new ListPropertyHandler();

	static PropertyHandler getPropertyHandler(Class clazz) {
		if (Map.class.isAssignableFrom(clazz)) {
			return mapPropertyHandler;
		} else if (List.class.isAssignableFrom(clazz)) {
			return listPropertyHandler;
		} else if (clazz.isArray()) {
			return arrayProperyHandler;
		} else {
			return beanProperyHandler;
		}
	}
}
