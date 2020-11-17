package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.Constant;

public class ElementHandlerManager {
	private static Map<String, ElementHandler> elementHandlerMap = new HashMap<String, ElementHandler>();
	static {
		elementHandlerMap.put(Constant.IF, new IfElementHandler());
		elementHandlerMap.put(Constant.REPEAT, new RepeatElementHandler());
		elementHandlerMap.put(Constant.MODEL, new ModelElementHandler());
		elementHandlerMap.put(Constant.ORDINARY, new OrdinaryElementHandler());
	}

	public static ElementHandler getElementHandler(Element element) {
		List<String> attributes = new ArrayList<String>();
		for (int i = 0; i < element.attributeCount(); i++) {
			attributes.add(element.attribute(i).getName());
		}
		if (attributes.contains(Constant.IF) && attributes.contains(Constant.REPEAT)) {
			if (attributes.indexOf(Constant.IF) < attributes.indexOf(Constant.REPEAT)) {
				return elementHandlerMap.get(Constant.IF);
			} else {
				return elementHandlerMap.get(Constant.REPEAT);
			}
		}
		if (attributes.contains(Constant.IF)) {
			return elementHandlerMap.get(Constant.IF);
		}
		if (attributes.contains(Constant.REPEAT)) {
			return elementHandlerMap.get(Constant.REPEAT);
		}
		if (attributes.contains(Constant.MODEL)) {
			return elementHandlerMap.get(Constant.MODEL);
		}
		return elementHandlerMap.get(Constant.ORDINARY);
	}
}
