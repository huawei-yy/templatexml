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
		try {
			return new SimpleDateFormat(pattern).parse(strVule);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

}
