package pers.yhw.templatexml.xmlhandler.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;

public class TransCodeFormat extends Format {
	Map<String, String> codeMap = new HashMap<String, String>();

	public TransCodeFormat(String pattern) {
		pattern = pattern.substring(pattern.indexOf("{")+1, pattern.lastIndexOf("}"));
		String[] notes = pattern.split(",");
		for (String note : notes) {
			String[] kv = note.split("=");
			codeMap.put(kv[0], kv[1]);
		}

	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj != null) {
			String code = codeMap.get(obj);
			if (code == null) {
				code = obj.toString();
			}
			toAppendTo.append(code);
		}
		return toAppendTo;
	}

	@Override
	public String parseObject(String source, ParsePosition pos) {
		return format(source);
	}
}
