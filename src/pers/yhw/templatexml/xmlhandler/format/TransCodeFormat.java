package pers.yhw.templatexml.xmlhandler.format;

import java.util.HashMap;
import java.util.Map;

import pers.yhw.templatexml.xmlhandler.Constant;

public class TransCodeFormat implements Format {

	@Override
	public String formatName() {
		return Constant.TRANSCODE;
	}

	private Map<String, String> getCodeMap(String pattern) {
		Map<String, String> codeMap = new HashMap<String, String>();
		pattern = pattern.substring(pattern.indexOf("{") + 1, pattern.lastIndexOf("}"));
		String[] notes = pattern.split(",");
		for (String note : notes) {
			String[] kv = note.split("=");
			codeMap.put(kv[0], kv[1]);
		}
		return codeMap;
	}

	@Override
	public String toStr(Object object, String pattern) {
		String code = "";
		if (object != null) {
			Map<String, String> codeMap = getCodeMap(pattern);
			code = codeMap.get(object);
			if (code == null) {
				code = object.toString();
			}
		}
		return code;
	}

	@Override
	public Object toObject(String strVule, String pattern) {
		return toStr(strVule, pattern);
	}
}
