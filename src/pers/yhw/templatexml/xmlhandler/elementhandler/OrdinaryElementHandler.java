package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

class OrdinaryElementHandler implements ElementHandler {
	@Override
	public void buildElement(Element element, Map<String, Object> objectVos) {
		if (element != null) {
			for (Element childElement : (List<Element>) element.elements()) {
				ElementHandlerManager.getElementHandler(childElement).buildElement(childElement, objectVos);
			}
		}
	}

}
