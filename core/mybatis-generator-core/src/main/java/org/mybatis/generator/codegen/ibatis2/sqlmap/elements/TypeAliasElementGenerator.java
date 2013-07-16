package org.mybatis.generator.codegen.ibatis2.sqlmap.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class TypeAliasElementGenerator extends AbstractXmlElementGenerator {

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("typeAlias");

		answer.addAttribute(new Attribute("alias", this.introspectedTable.getBaseType()));
		answer.addAttribute(new Attribute("type", this.introspectedTable
				.getBaseRecordType()));

		if (this.context.getPlugins().sqlMapInsertSelectiveElementGenerated(
				answer, this.introspectedTable))
			parentElement.addElement(answer);
	}

}
