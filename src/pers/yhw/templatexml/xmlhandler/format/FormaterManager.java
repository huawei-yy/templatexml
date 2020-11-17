package pers.yhw.templatexml.xmlhandler.format;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import pers.yhw.templatexml.xmlhandler.Constant;

public class FormaterManager {
	public static Format getFormater(String formatExpression) {
		int formatNameSplitIndex = formatExpression.indexOf(Constant.FORMATNAMESPLIT);
		String name = "";
		String pattern = null;
		if (formatNameSplitIndex > 0) {
			name = formatExpression.substring(0, formatNameSplitIndex);
			pattern = formatExpression.substring(formatNameSplitIndex + 1, formatExpression.length());
		} else {
			name = formatExpression;
		}
		switch (name) {
		case Constant.DATEFORMAT:
			return new SimpleDateFormat(pattern);
		case Constant.NUMBERFORMAT:
			return new DecimalFormat(pattern);
		case Constant.TRANSCODE:
			return new TransCodeFormat(pattern);
		}
		return null;
	}
}
