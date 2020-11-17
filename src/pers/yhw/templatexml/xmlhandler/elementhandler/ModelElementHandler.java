package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.text.Format;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.BeanPropertyUtils;
import pers.yhw.templatexml.xmlhandler.Constant;
import pers.yhw.templatexml.xmlhandler.format.FormaterManager;

class ModelElementHandler implements ElementHandler {

	@Override
	public void buildElement(Element element, Map<String, Object> objectVos) {
		Attribute attribute = element.attribute(Constant.MODEL);
		// ɾ��x-repeat����
		element.remove(attribute);
		ModelAttribute modelAttribute = parseModelAttribute(attribute);
		Object value = BeanPropertyUtils.getProperty(objectVos, modelAttribute.getValueExpression());
		String valueStr = "";
		if (value != null) {
			if (modelAttribute.isHasFormat()) {
				Format formater = FormaterManager.getFormater(modelAttribute.getFormatExpression());
				valueStr = formater.format(value);
			} else {
				valueStr = value.toString();
			}
		}
		element.setText(valueStr);
	}

	private ModelAttribute parseModelAttribute(Attribute attribute) {
		String attributeExpression = attribute.getText();
		int formatSplitIndex = attributeExpression.indexOf(Constant.FORMATSPLIT);
		ModelAttribute modelAttribute = new ModelAttribute();
		if (formatSplitIndex > 0) {
			modelAttribute.setHasFormat(true);
			modelAttribute.setValueExpression(attributeExpression.substring(0, formatSplitIndex).trim());
			String formatExpression = attributeExpression.substring(formatSplitIndex + 1, attributeExpression.length())
					.trim();
			modelAttribute.setFormatExpression(formatExpression);
		} else {
			modelAttribute.setHasFormat(false);
			modelAttribute.setValueExpression(attributeExpression.trim());
		}
		return modelAttribute;
	}

	private class ModelAttribute {
		private String valueExpression;
		private boolean hasFormat = false;
		private String formatExpression;

		public String getValueExpression() {
			return valueExpression;
		}

		public void setValueExpression(String valueExpression) {
			this.valueExpression = valueExpression;
		}

		public boolean isHasFormat() {
			return hasFormat;
		}

		public void setHasFormat(boolean hasFormat) {
			this.hasFormat = hasFormat;
		}

		public String getFormatExpression() {
			return formatExpression;
		}

		public void setFormatExpression(String formatExpression) {
			this.formatExpression = formatExpression;
		}

	}

}
