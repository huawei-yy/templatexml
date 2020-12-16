package pers.yhw.templatexml.beanpropertyutils;

public interface PropertyHandler {
	PropertyInfo getProperty(PropertyInfo upPropertyInfo, String subPath);

	void setProperty(PropertyInfo upPropertyInfo, String subPath, Object value);
	
}
