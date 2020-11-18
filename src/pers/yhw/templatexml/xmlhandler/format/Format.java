package pers.yhw.templatexml.xmlhandler.format;

public interface Format {
	String formatName();

	String toStr(Object object, String pattern);

	Object toObject(String strVule, String pattern);
}