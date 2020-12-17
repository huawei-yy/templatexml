package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlParser {

	public static <T> T parstxml(Class<T> returnType, String xmlTemplate, String targetXml)
			throws DocumentException, InstantiationException, IllegalAccessException {
		Document xmlTemplateDoc = DocumentHelper.parseText(xmlTemplate);
		Document targetDoc = DocumentHelper.parseText(targetXml);
		Object returnObj = returnType.newInstance();
		parseDocument(returnObj, xmlTemplateDoc, targetDoc);
		return (T) returnObj;
	}

	public static void parseDocument(Object object, Document templateDoc, Document targetDoc) throws DocumentException {
		templateDoc = (Document) templateDoc.clone();
		Map<String, Object> objectVos = new HashMap<String, Object>();
		objectVos.put("$", object);
		Element templateRootElement = templateDoc.getRootElement();
		Element targetRootElement = targetDoc.getRootElement();
		if (!templateRootElement.getName().equals(targetRootElement.getName())) {
			throw new RuntimeException("the targetXml does not match the template");
		}
		ElementHandler elementHandler = ElementHandlerManager.getElementHandler(targetRootElement);
		elementHandler.parseElement(templateRootElement, targetRootElement, objectVos);
	}

}
