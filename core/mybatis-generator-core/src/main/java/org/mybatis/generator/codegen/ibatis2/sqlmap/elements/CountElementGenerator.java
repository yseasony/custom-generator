package org.mybatis.generator.codegen.ibatis2.sqlmap.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CountElementGenerator extends AbstractXmlElementGenerator {

	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select");

		answer.addAttribute(new Attribute("id", "selectPageCount"));
		answer.addAttribute(new Attribute("parameterClass", "java.util.Map"));

		answer.addAttribute(new Attribute("resultClass", "java.lang.Long"));

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from ");
		sb.append(this.introspectedTable
				.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement isParameterPresenteElement = new XmlElement(
				"isParameterPresent");
		answer.addElement(isParameterPresenteElement);

		XmlElement includeElement = new XmlElement("include");
		sb.setLength(0);
		sb.append(this.introspectedTable.getIbatis2SqlMapNamespace());
		sb.append('.');
		sb.append(this.introspectedTable.getBaseWhereId());

		includeElement.addAttribute(new Attribute("refid", sb.toString()));

		isParameterPresenteElement.addElement(includeElement);

		if (this.context.getPlugins().sqlMapCountByExampleElementGenerated(
				answer, this.introspectedTable))
			parentElement.addElement(answer);
	}
}
