package pers.yhw.templatexml.xmlhandler.format;

import java.util.HashMap;
import java.util.Map;

import pers.yhw.templatexml.xmlhandler.Constant;

public class FormaterManager {
	private static Map<String, Format> formatMap = new HashMap<String, Format>();
	static {
		regist(new TransCodeFormat());
		regist(new NumberFormat());
		regist(new DateFormat());
	}

	private static FormatInfo parse(String formatExpression) {
		int formatNameSplitIndex = formatExpression.indexOf(Constant.FORMATNAMESPLIT);
		String name = "";
		String pattern = null;
		if (formatNameSplitIndex > 0) {
			name = formatExpression.substring(0, formatNameSplitIndex);
			pattern = formatExpression.substring(formatNameSplitIndex + 1, formatExpression.length());
		} else {
			name = formatExpression;
		}
		FormatInfo formatInfo = new FormatInfo();
		formatInfo.setName(name);
		formatInfo.setPattern(pattern);
		return formatInfo;
	}

	public static String toStr(Object object, String formatExpression) {
		FormatInfo formatInfo = parse(formatExpression);
		Format format = formatMap.get(formatInfo.getName());
		return format.toStr(object, formatInfo.getPattern());
	}

	public static Object toObject(String strVule, String formatExpression) {
		FormatInfo formatInfo = parse(formatExpression);
		Format format = formatMap.get(formatInfo.getName());
		return format.toObject(strVule, formatInfo.getPattern());
	}

	public static void regist(Format format) {
		formatMap.put(format.formatName(), format);
	}

}
