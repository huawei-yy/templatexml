package pers.yhw.templatexml.xmlhandler;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.elementhandler.ElementHandler;
import pers.yhw.templatexml.xmlhandler.elementhandler.ElementHandlerManager;

public class XmlBuilder {
	public static String buildxml(Object object, String xmlTemplate) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlTemplate);
		return buildDocument(object, document);
	}

	public static String buildxml(Object object, Document xmlTemplate) throws DocumentException {
		Document document = (Document) xmlTemplate.clone();
		return buildDocument(object, document);
	}

	private static String buildDocument(Object object, Document document) throws DocumentException {
		Map<String, Object> objectVos = new HashMap<String, Object>();
		objectVos.put("$",  object);
		Element rootElement = document.getRootElement();
		ElementHandler elementHandler = ElementHandlerManager.getElementHandler(rootElement);
		elementHandler.buildElement(rootElement, objectVos);
		return document.asXML();
	}
}
