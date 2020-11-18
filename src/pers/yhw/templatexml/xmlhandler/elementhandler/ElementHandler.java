package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.dom4j.Element;

public interface ElementHandler {
	String applyToAttributeName();

	/**
	 * 跟据属性处理模板元素
	 * 
	 * @param templateElement模板元素
	 * @param attribute属性
	 * @param objectVos值
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	void buildElement(Element element, Map<String, Object> objectVos);

}
