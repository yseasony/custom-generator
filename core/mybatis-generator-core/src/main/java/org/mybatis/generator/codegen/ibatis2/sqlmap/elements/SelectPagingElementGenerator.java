package org.mybatis.generator.codegen.ibatis2.sqlmap.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.plugins.V;

public class SelectPagingElementGenerator extends AbstractXmlElementGenerator {
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select");
		answer.addAttribute(new Attribute("id", "selectPage"));

		answer.addAttribute(new Attribute("resultClass", this.introspectedTable
				.getBaseType()));

		answer.addAttribute(new Attribute("parameterClass","java.util.Map"));

		if ("oracle.jdbc.OracleDriver".equalsIgnoreCase(V.driver)) {
			answer.addElement(new TextElement(
					"<![CDATA[select * from ( select row_.*, rownum rownum_ from ( ]]>"));
		}

		answer.addElement(new TextElement("select"));

		StringBuilder sb = new StringBuilder();
		if (StringUtility.stringHasValue(this.introspectedTable
				.getSelectByExampleQueryId())) {
			sb.append('\'');
			sb.append(this.introspectedTable.getSelectByExampleQueryId());
			sb.append("' as QUERYID,");
			answer.addElement(new TextElement(sb.toString()));
		}

		answer.addElement(getBaseColumnListElement());

		sb.setLength(0);
		sb.append("from ");
		sb.append(this.introspectedTable
				.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement isParameterPresenteElement = new XmlElement(
				"isParameterPresent");
		answer.addElement(isParameterPresenteElement);

		XmlElement includeElement = new XmlElement("include");
		includeElement.addAttribute(new Attribute("refid",
				this.introspectedTable.getIbatis2SqlMapNamespace() + "."
						+ this.introspectedTable.getBaseWhereId()));
		isParameterPresenteElement.addElement(includeElement);

		XmlElement isNotNullElement = new XmlElement("isNotNull");
		isNotNullElement
				.addAttribute(new Attribute("property", "orderByClause"));
		isNotNullElement
				.addElement(new TextElement("order by $orderByClause$"));

		answer.addElement(isNotNullElement);

		if ("oracle.jdbc.OracleDriver".equalsIgnoreCase(V.driver))
			answer.addElement(new TextElement(
					"<![CDATA[) row_ where rownum <= #page.offset# ) where rownum_ > #page.pageSize#]]>"));
		else {
			answer.addElement(new TextElement("LIMIT #page.offset#, #page.pageSize#"));
		}

		if (this.context.getPlugins()
				.sqlMapSelectByExampleWithBLOBsElementGenerated(answer,
						this.introspectedTable))
			parentElement.addElement(answer);
	}
}
