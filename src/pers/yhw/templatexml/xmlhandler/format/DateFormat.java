package pers.yhw.templatexml.xmlhandler.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pers.yhw.templatexml.xmlhandler.Constant;

public class DateFormat implements Format {

	@Override
	public String formatName() {
		return Constant.DATEFORMAT;
	}

	@Override
	public String toStr(Object object, String pattern) {
		return new SimpleDateFormat(pattern).format((Date) object);
	}

	@Override
	public Date toObject(String strVule, String pattern) {
		if (strVule != null) {
			if (pattern == null) {
				if (strVule.length() == 10) {
					pattern = "yyyy-MM-dd";
				} else if (strVule.length() == "yyyy-MM-dd HH:mm:ss".length()) {
					pattern = "yyyy-MM-dd HH:mm:ss";
				} else if (strVule.length() == 29 && strVule.charAt(26) == ':' && strVule.charAt(28) == '0') {
					pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
				} else if (strVule.length() == 23 && strVule.charAt(19) == ',') {
					pattern = "yyyy-MM-dd HH:mm:ss,SSS";
				} else {
					pattern = "yyyy-MM-dd HH:mm:ss.SSS";
				}
			}
			if (strVule.length() > pattern.length()) {
				strVule = strVule.substring(0, pattern.length());
			} else if (strVule.length() < pattern.length()) {
				pattern = pattern.substring(0, strVule.length());
			}
			try {
				return new SimpleDateFormat(pattern).parse(strVule);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return null;

	}

}
