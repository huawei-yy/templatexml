package pers.yhw.templatexml;

import org.dom4j.DocumentException;

import pers.yhw.templatexml.xmlhandler.XmlBuilder;
import pers.yhw.templatexml.xmlhandler.XmlParser;

public class XmlBuildAndParse {

	public static String buildxml(Object object, String xmlTemplate) throws DocumentException {
		return XmlBuilder.buildxml(object, xmlTemplate);
	}

	public static <T> T parstxml(Class<T> returnType, String xmlTemplate, String targetXml) {
		return XmlParser.parstxml(returnType, xmlTemplate, targetXml);
	}

}
