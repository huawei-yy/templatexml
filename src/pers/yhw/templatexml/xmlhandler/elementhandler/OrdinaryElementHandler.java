package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.Constant;

class OrdinaryElementHandler implements ElementHandler {
	@Override
	public void buildElement(Element templateElement, Map<String, Object> objectVos) {
		if (templateElement != null) {
			for (Element childElement : (List<Element>) templateElement.elements()) {
				ElementHandlerManager.getElementHandler(childElement).buildElement(childElement, objectVos);
			}
		}
	}

	@Override
	public String applyToAttributeName() {
		return Constant.ORDINARY;
	}

	@Override
	public void parseElement(Element templateElement, Element targetElement, Map<String, Object> objectVos) {
		if (templateElement != null) {
			for (Element templateChildElement : (List<Element>) templateElement.elements()) {
				Element targetChildElement = targetElement.element(templateChildElement.getName());
				ElementHandlerManager.getElementHandler(templateChildElement).parseElement(templateChildElement,
						targetChildElement, objectVos);
			}
		}
	}
}
