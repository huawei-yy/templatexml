package pers.yhw.templatexml.xmlhandler.elementhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.dom4j.Attribute;
import org.dom4j.Element;

import pers.yhw.templatexml.xmlhandler.BeanPropertyUtils;
import pers.yhw.templatexml.xmlhandler.Constant;

class IfElementHandler implements ElementHandler {
	ScriptEngine engine = null;

	IfElementHandler() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		// 运行js的eval函数
		String script = "function evalScript(script) { return eval(script); }";
		try {
			engine.eval(script);
		} catch (ScriptException e) {
		}
		this.engine = engine;
	}

	@Override
	public void buildElement(Element element, Map<String, Object> objectVos) {
		Attribute attribute = element.attribute(Constant.IF);
		// 删除参数
		element.remove(attribute);
		IfAttribute ifAttribute = parseAttribute(attribute);
		boolean isTrue = logicalOperate(ifAttribute, objectVos);
		if (isTrue) {
			ElementHandlerManager.getElementHandler(element).buildElement(element, objectVos);
		} else {
			element.getParent().remove(element);
		}
	}

	private boolean logicalOperate(IfAttribute ifAttribute, Map<String, Object> objectVos) {
		String logicExpression = ifAttribute.getLogicExpression();
		int replaceIndex = 0;
		StringBuffer logicExpressionBuf = new StringBuffer(logicExpression);
		for (String variableExpression : ifAttribute.getVariableExpressions()) {
			Object variable = BeanPropertyUtils.getProperty(objectVos, variableExpression);
			String variableStr = "null";
			if (variable != null) {
				variableStr = variable.toString();
			}
			int startIndex = logicExpressionBuf.indexOf(variableExpression, replaceIndex);
			int endIndex = startIndex + variableExpression.length();
			logicExpressionBuf.replace(startIndex, endIndex, variableStr);
			replaceIndex = startIndex + variableStr.length();
		}

		try {
			return (boolean) ((Invocable) engine).invokeFunction("evalScript", logicExpressionBuf.toString());
		} catch (NoSuchMethodException | ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	private IfAttribute parseAttribute(Attribute attribute) {
		IfAttribute ifAttribute = new IfAttribute();
		String attributeExpression = attribute.getText();
		ifAttribute.setLogicExpression(attributeExpression);
		String[] attributeExpressionSub = attributeExpression.split("[!=><\\(\\)\\|&]");
		for (String param : attributeExpressionSub) {
			param = param.trim();
			// 如果是双引号或单引号包括还有数字认为是常量
			if (Pattern.matches("\".*\"|'.*'|[0-9]+|null|^$", param)) {
			} else {
				ifAttribute.getVariableExpressions().add(param);
			}
		}
		return ifAttribute;
	}

	private class IfAttribute {
		String logicExpression;
		List<String> variableExpressions = new ArrayList<String>();

		public String getLogicExpression() {
			return logicExpression;
		}

		public void setLogicExpression(String logicExpression) {
			this.logicExpression = logicExpression;
		}

		public List<String> getVariableExpressions() {
			return variableExpressions;
		}

		public void setVariableExpressions(List<String> variableExpressions) {
			this.variableExpressions = variableExpressions;
		}

	}

	@Override
	public String applyToAttributeName() {
		return Constant.IF;
	}

}
