package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import pers.yhw.templatexml.beanpropertyutils.BeanPropertyUtils;
import pers.yhw.templatexml.xmlhandler.Constant;

class RepeatElementHandler implements ElementHandler {
	RepeatElementHandler() {

	}

	@Override
	public void buildElement(Element templateElement, Map<String, Object> objectVos) {
		Attribute attribute = getAndRemoveAttribute(templateElement);
		// 根据对象获得要取值对象的
		RepeatAttribute repeatAttribute = parseRepeatAttribute(attribute);
		String eachExpression = repeatAttribute.getEachExpression();
		Object repeatableObject = BeanPropertyUtils.getProperty(objectVos, repeatAttribute.getListExpression());
		int size = getSize(repeatableObject);
		// 循环list
		Element parentElement = templateElement.getParent();
		for (int i = 0; i < size; i++) {
			Object eachObject = BeanPropertyUtils.getProperty(repeatableObject, "[" + i + "]");
			// 添加节点
			Element newElement = (Element) templateElement.clone();
			parentElement.add(newElement);
			objectVos.put(eachExpression, eachObject);
			ElementHandler elementHandler = ElementHandlerManager.getElementHandler(newElement);
			elementHandler.buildElement(newElement, objectVos);
			objectVos.remove(eachExpression);
		}
		// 删除模板对象
		parentElement.remove(templateElement);
	}

	private int getSize(Object repeatableObject) {
		int size = 0;
		if (repeatableObject != null) {
			if (Collection.class.isAssignableFrom(repeatableObject.getClass())) {
				size = ((Collection) repeatableObject).size();
			} else if (repeatableObject.getClass().isArray()) {
				size = Array.getLength(repeatableObject);
			}
		}
		return size;
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

	@Override
	public void parseElement(Element templateElement, Element targetElement, Map<String, Object> objectVos) {
		Attribute attribute = getAndRemoveAttribute(templateElement);
		String templateElementName = templateElement.getName();
		List<Element> targetElements = targetElement.getParent().elements(templateElementName);
		int size = targetElements.size();
		RepeatAttribute repeatAttribute = parseRepeatAttribute(attribute);
		String eachExpression = repeatAttribute.getEachExpression();
		Object repeatableObject = BeanPropertyUtils.getPropertyOrDefult(objectVos, repeatAttribute.getListExpression());
		for (int i = 0; i < size; i++) {
			Object eachObject = BeanPropertyUtils.getPropertyOrDefult(objectVos,
					repeatAttribute.getListExpression() + "[" + i + "]");
			objectVos.put(eachExpression, eachObject);
			ElementHandler elementHandler = ElementHandlerManager.getElementHandler(templateElement);
			elementHandler.parseElement((Element) templateElement.clone(), targetElements.get(i), objectVos);
			objectVos.remove(eachExpression);
		}
	}
}
