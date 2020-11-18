package pers.yhw.templatexml.xmlhandler.format;

import java.text.DecimalFormat;

import pers.yhw.templatexml.xmlhandler.Constant;

public class NumberFormat implements Format {

	@Override
	public String formatName() {
		return Constant.NUMBERFORMAT;
	}

	@Override
	public String toStr(Object object, String pattern) {
		return new DecimalFormat(pattern).format(object);
	}

	@Override
	public Object toObject(String strVule, String pattern) {
		return toStr(strVule, pattern);
	}

}
