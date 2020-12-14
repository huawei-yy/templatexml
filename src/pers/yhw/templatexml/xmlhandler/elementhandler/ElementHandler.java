package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.Constant;

public interface ElementHandler {
	String applyToAttributeName();

	default Attribute getAndRemoveAttribute(Element templateElement) {
		Attribute attribute = templateElement.attribute(applyToAttributeName());
		// ɾ��x-repeat����
		templateElement.remove(attribute);
		return attribute;
	}

	/**
	 * �������Դ���ģ��Ԫ��
	 * 
	 * @param templateElementģ��Ԫ��
	 * @param attribute����
	 * @param objectVosֵ
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	void buildElement(Element templateElement, Map<String, Object> objectVos);

	void parseElement(Element templateElement, Element targetElement, Map<String, Object> objectVos);

}
