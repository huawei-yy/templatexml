package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.Constant;

public class ElementHandlerManager {
	private static Map<String, ElementHandler> elementHandlerMap = new HashMap<String, ElementHandler>();
	static {
		regist(new IfElementHandler());
		regist(new RepeatElementHandler());
		regist(new ModelElementHandler());
		regist(new OrdinaryElementHandler());
	}

	static ElementHandler getElementHandler(Element element) {
		for (int i = 0; i < element.attributeCount(); i++) {
			String attributeName = element.attribute(i).getName();
			ElementHandler elementHandler = elementHandlerMap.get(attributeName);
			if (elementHandler != null) {
				return elementHandler;
			}
		}
		return elementHandlerMap.get(Constant.ORDINARY);
	}

	public static void regist(ElementHandler elementHandler) {
		elementHandlerMap.put(elementHandler.applyToAttributeName(), elementHandler);
	}

}
