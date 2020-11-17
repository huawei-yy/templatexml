package pers.yhw.templatexml.xmlhandler;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanPropertyUtils {
	public static Object getProperty(final Object bean, final String name) {
		try {
			return PropertyUtils.getProperty(bean, name);
		} catch (NestedNullException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
