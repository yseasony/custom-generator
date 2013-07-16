package org.mybatis.generator.codegen.ibatis2.sqlmap.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.ibatis2.Ibatis2FormattingUtilities;

public class BaseWhereElementGenerator extends AbstractXmlElementGenerator
{
  public void addElements(XmlElement parentElement)
  {
    XmlElement answer = new XmlElement("sql");

    answer.addAttribute(new Attribute("id", this.introspectedTable.getBaseWhereId()));
    XmlElement whereElement = new XmlElement("dynamic");
    whereElement.addAttribute(new Attribute("prepend", "where"));
    answer.addElement(whereElement);

    for (IntrospectedColumn introspectedColumn : this.introspectedTable.getAllColumns())
    {
      String isNot = null;
      if (introspectedColumn.getJdbcTypeName().indexOf("CHAR") >= 0)
        isNot = "isNotEmpty";
      else {
        isNot = "isNotNull";
      }

      StringBuilder sbc = new StringBuilder();
      sbc.append("<").append(isNot).append(" prepend=\"AND\" property=\"").append(introspectedColumn.getJavaProperty()).append("\"> ")
        .append(introspectedColumn.getActualColumnName()).append(" = ")
        .append(Ibatis2FormattingUtilities.getParameterClause(introspectedColumn)).append(" </").append(isNot).append(">");
      whereElement.addElement(new TextElement(sbc.toString()));
    }

    if (this.context.getPlugins().sqlMapInsertSelectiveElementGenerated(
      answer, this.introspectedTable))
      parentElement.addElement(answer);
  }
}
