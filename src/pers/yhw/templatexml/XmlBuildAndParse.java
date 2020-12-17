package pers.yhw.templatexml;

import org.dom4j.DocumentException;

import pers.yhw.templatexml.xmlhandler.elementhandler.XmlBuilder;
import pers.yhw.templatexml.xmlhandler.elementhandler.XmlParser;

public class XmlBuildAndParse {

	public static String buildxml(Object object, String xmlTemplate) throws DocumentException {
		return XmlBuilder.buildxml(object, xmlTemplate);
	}

	public static <T> T parstxml(Class<T> returnType, String xmlTemplate, String targetXml) throws InstantiationException, IllegalAccessException, DocumentException  {
		return XmlParser.parstxml(returnType, xmlTemplate, targetXml);
	}

}
