package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.Arrays;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.BeanPropertyUtils;
import pers.yhw.templatexml.xmlhandler.Constant;
import pers.yhw.templatexml.xmlhandler.RegExCheck;

class RepeatElementHandler implements ElementHandler {

	@Override
	public void buildElement(Element element, Map<String, Object> objectVos) {
		Attribute attribute = element.attribute(Constant.REPEAT);
		// 删除x-repeat参数
		element.remove(attribute);
		// 根据对象获得要取值对象的
		RepeatAttribute repeatAttribute = parseRepeatAttribute(attribute);
		String eachExpression = repeatAttribute.getEachExpression();
		Object realListObject = BeanPropertyUtils.getProperty(objectVos, repeatAttribute.getListExpression());
		Iterable iterableObject = toIterable(realListObject, repeatAttribute.getListExpression());
		// 循环list
		Element parentElement = element.getParent();
		if (iterableObject != null)
			for (Object object : iterableObject) {
				// 添加节点
				Element newElement = (Element) element.clone();
				parentElement.add(newElement);
				objectVos.put(eachExpression, object);
				ElementHandler elementHandler = ElementHandlerManager.getElementHandler(newElement);
				elementHandler.buildElement(newElement, objectVos);
				objectVos.remove(eachExpression);
			}
		// 删除模板对象
		parentElement.remove(element);
	}

	private Iterable toIterable(Object realListObject, String listExpression) {
		Iterable returnObject = null;
		if (realListObject != null) {
			if (Iterable.class.isAssignableFrom(realListObject.getClass())) {
				returnObject = (Iterable) realListObject;
			} else if (realListObject.getClass().isArray()) {
				returnObject = Arrays.asList(returnObject);
			} else {
				throw new RuntimeException(listExpression + "is not iterable");
			}
		}
		return returnObject;
	}

	private RepeatAttribute parseRepeatAttribute(Attribute attribute) {
		String attributeExpression = attribute.getText();
		if (!RegExCheck.repeatExPattern.matcher(attributeExpression).matches()) {
			throw new IllegalArgumentException(attributeExpression + " is illegal x-if expression");
		}
		String[] attributeExpressionSub = attributeExpression.split(Constant.IN);
		RepeatAttribute repeatAttribute = new RepeatAttribute();
		repeatAttribute.setEachExpression(attributeExpressionSub[0].trim());
		repeatAttribute.setListExpression(attributeExpressionSub[1].trim());
		return repeatAttribute;
	}

	private class RepeatAttribute {
		private String eachExpression;
		private String ListExpression;

		public String getEachExpression() {
			return eachExpression;
		}

		public void setEachExpression(String eachExpression) {
			this.eachExpression = eachExpression;
		}

		public String getListExpression() {
			return ListExpression;
		}

		public void setListExpression(String listExpression) {
			ListExpression = listExpression;
		}

	}

	@Override
	public String applyToAttributeName() {
		return Constant.REPEAT;
	}
}
