/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.cmcc.mybatis.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * MySQL 分页生成插件。
 *
 */
public final class PagePlugin extends PluginAdapter {

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {        // add field, getter, setter for limit clause
        addPage(topLevelClass, introspectedTable, "page");
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        XmlElement page = new XmlElement("if");
        page.addAttribute(new Attribute("test", "page != null "));
        page.addElement(new TextElement("limit #{page.start} , #{page.count}"));
        element.addElement(page);


//        XmlElement ifelement = new XmlElement("if"); //$NON-NLS-1$
//        ifelement.addAttribute(new Attribute("test", "start>=0 and limit>0")); //$NON-NLS-1$ //$NON-NLS-2$
//        ifelement.addElement(new TextElement("limit ${start},${limit}")); //$NON-NLS-1$
//        element.addElement(ifelement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    private String getModelPackage(IntrospectedTable introspectedTable, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getJavaModelGeneratorConfiguration().getTargetPackage());
        sb.append(introspectedTable.getFullyQualifiedTable().getSubPackageForModel(StringUtility.isTrue(context.getJavaModelGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES))));
        String pakkage = sb.toString();
        return pakkage;
    }

    /**
     * @param topLevelClass
     * @param introspectedTable
     * @param name
     */
    private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
                         String name) {
        Context context2 = introspectedTable.getContext();
        String pakkage = getModelPackage(introspectedTable, context);
        String type = pakkage + ".Page";
        topLevelClass.addImportedType(new FullyQualifiedJavaType(type));
        CommentGenerator commentGenerator = context2.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(new FullyQualifiedJavaType(type));
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(type), name));


        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(type));
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

//
//        // add field, getter, setter for limit clause
//        CommentGenerator commentGenerator = context.getCommentGenerator();
//        Method method = new Method();
//        Field field = new Field();
//        field.setVisibility(JavaVisibility.PROTECTED);
//        field.setType(FullyQualifiedJavaType.getIntInstance());
//        field.setName("start"); //$NON-NLS-1$
//        commentGenerator.addFieldComment(field, introspectedTable);
//        topLevelClass.addField(field);
//
//        method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setName("setStart"); //$NON-NLS-1$
//        method.addParameter(new Parameter(FullyQualifiedJavaType
//                .getIntInstance(), "start")); //$NON-NLS-1$
//        method.addBodyLine("this.start = start;"); //$NON-NLS-1$
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);
//
//        method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
//        method.setName("getStart"); //$NON-NLS-1$
//        method.addBodyLine("return start;"); //$NON-NLS-1$
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);
//
//
//        field = new Field();
//        field.setVisibility(JavaVisibility.PROTECTED);
//        field.setType(FullyQualifiedJavaType.getIntInstance());
//        field.setName("limit"); //$NON-NLS-1$
//        commentGenerator.addFieldComment(field, introspectedTable);
//        topLevelClass.addField(field);
//
//        method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setName("setLimit"); //$NON-NLS-1$
//        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "limit")); //$NON-NLS-1$
//        method.addBodyLine("this.limit = limit;"); //$NON-NLS-1$
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);
//
//        method = new Method();
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
//        method.setName("getLimit"); //$NON-NLS-1$
//        method.addBodyLine("return limit;"); //$NON-NLS-1$
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(method);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}