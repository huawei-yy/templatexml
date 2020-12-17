package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

public interface ElementHandler {
	String applyToAttributeName();

	default Attribute getAndRemoveAttribute(Element templateElement) {
		Attribute attribute = templateElement.attribute(applyToAttributeName());
		// 删除x-repeat参数
		templateElement.remove(attribute);
		return attribute;
	}

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
	void buildElement(Element templateElement, Map<String, Object> objectVos);

	/**
	 * 根据模板解析
	 * @param templateElement
	 * @param targetElement
	 * @param objectVos
	 */
	void parseElement(Element templateElement, Element targetElement, Map<String, Object> objectVos);

}
