package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.dom4j.Element;

public interface ElementHandler {
	String applyToAttributeName();

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
	void buildElement(Element element, Map<String, Object> objectVos);

}
