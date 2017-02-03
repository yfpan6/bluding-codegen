/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator.util;

import com.bluding.codegen.context.Context;
import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.FieldConfiguration;
import com.bluding.codegen.context.model.ModelConfiguration;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import com.bluding.codegen.util.StringUtil;

import java.util.Date;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class MyronCommentGenerator {

    private MyronCommentGenerator() {
        super();
    }

    public static void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    public static void addComment(XmlElement xmlElement) {
        if (isSuppressedAllComments()) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement
                .addElement(new TextElement(
                        "  This element is automatically generated by MyBatis Generator, do not modify.")); //$NON-NLS-1$

        String s = getDateString();
        if (s != null && s.length() > 0) {
            sb.setLength(0);
            sb.append("  This element was generated on "); //$NON-NLS-1$
            sb.append(s);
            sb.append('.');
            xmlElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    public static void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     *
     * @param javaElement
     *            the java element
     */
    protected static void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
//        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
//        StringBuilder sb = new StringBuilder();
//        sb.append(" * "); //$NON-NLS-1$
//        sb.append(MergeConstants.NEW_ELEMENT_TAG);
//        if (markAsDoNotDelete) {
//            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
//        }
//        String s = getDateString();
//        if (s != null && s.length() > 0) {
//            sb.append(' ');
//            sb.append(s);
//        }
//        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    protected static String getDateString() {
        if (isSuppressedAllComments()) {
            return null;
        } else {
            return new Date().toString();
        }
    }

    /**
     *
     * @param innerClass
     * @param modelConfiguration String catalog, String schema, String tableName, char separator
     *                    eg: separator=.  catalog.schema.tableName
     */
    public static void addClassComment(InnerClass innerClass,
                                       ModelConfiguration modelConfiguration,
                                       String[] commoneLines) {
        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * " + modelConfiguration.getEntityDesc());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);


        addCommentLine(innerClass, sb, commoneLines);

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(modelConfiguration.getFullyQualifiedTableText());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);

        innerClass.addJavaDocLine(" *");

        Configuration configuration = Context.getCurrConfigOrEmptyConfig();
        sb.append(" * @Author ").append(configuration.getAuthor());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);

        sb.append(" * @Date ").append(Context.getToday());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, false);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    private static void addCommentLine(JavaElement je, StringBuilder sb, String[] commoneLines) {
        if (commoneLines != null && commoneLines.length > 0) {
            for (String line : commoneLines) {
                sb.append(" * ").append(line);
                je.addJavaDocLine(sb.toString());
                sb.setLength(0);
            }
        }
    }
    public static void addJavaComment(JavaElement javaElement,
                                      ModelConfiguration modelConfiguration) {
        addJavaComment(javaElement, modelConfiguration, null);
    }

    /**
     *
     * @param innerClass
     * @param modelConfiguration String catalog, String schema, String tableName, char separator
     *                    eg: separator=.  catalog.schema.tableName
     */
    public static void addJavaComment(JavaElement innerClass,
                                      ModelConfiguration modelConfiguration,
                                       String[] commoneLines) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * " + modelConfiguration.getEntityDesc());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);


        addCommentLine(innerClass, sb, commoneLines);

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(modelConfiguration.getFullyQualifiedTableText());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);

        innerClass.addJavaDocLine(" *");

        Configuration configuration = Context.getCurrConfigOrEmptyConfig();
        sb.append(" * @Author ").append(configuration.getAuthor());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);

        sb.append(" * @Date ").append(Context.getToday());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, false);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }



    /**
     *
     * @param innerClass
     * @param modelConfiguration String catalog, String schema, String tableName, char separator
     *                    eg: separator=.  catalog.schema.tableName
     */
    public static void addClassComment(InnerClass innerClass,
                                       ModelConfiguration modelConfiguration) {
        addClassComment(innerClass, modelConfiguration, null);
    }

    public static void addEnumComment(InnerEnum innerEnum,
                                      ModelConfiguration modelConfiguration) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum
                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
        sb.append(modelConfiguration.getFullyQualifiedTableText());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addFieldComment(Field field,
                                       FieldConfiguration fieldConfiguration,
                                       String[] commoneLines) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**"); //$NON-NLS-1$

        sb.append(" * "); //$NON-NLS-1$
        if (!StringUtil.isBLank(fieldConfiguration.getComment())) {
            sb.append(fieldConfiguration.getComment());
        } else if (!StringUtil.isBLank(fieldConfiguration.getFieldLabel())) {
            sb.append(fieldConfiguration.getFieldLabel());
        } else {
            sb.append(fieldConfiguration.getColumnName());
        }
        field.addJavaDocLine(sb.toString());
        sb.setLength(0);

        addCommentLine(field, sb, commoneLines);

        sb.append(" * "); //$NON-NLS-1$
        sb.append(fieldConfiguration.getModelConfiguration().getFullyQualifiedTableText());
        sb.append('.');
        sb.append(fieldConfiguration.getColumnName());
        field.addJavaDocLine(sb.toString());

        addJavadocTag(field, false);

        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addFieldComment(Field field,
                                       FieldConfiguration fieldConfiguration) {
        addFieldComment(field, fieldConfiguration, null);
    }

    public static void addGeneralMethodComment(Method method,
                                               ModelConfiguration modelConfiguration) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method
                .addJavaDocLine(" * This method was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This method corresponds to the database table "); //$NON-NLS-1$
        sb.append(modelConfiguration.getFullyQualifiedTableText());
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addGetterComment(Method method,
                                        FieldConfiguration fieldConfiguration,
                                        String[] commoneLines) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**"); //$NON-NLS-1$

        sb.append(" * "); //$NON-NLS-1$
        if (!StringUtil.isBLank(fieldConfiguration.getComment())) {
            sb.append(fieldConfiguration.getComment());
        } else if (!StringUtil.isBLank(fieldConfiguration.getFieldLabel())) {
            sb.append(fieldConfiguration.getFieldLabel());
        } else {
            sb.append(fieldConfiguration.getColumnName());
        }
        method.addJavaDocLine(sb.toString());
        sb.setLength(0);

        addCommentLine(method, sb, commoneLines);

        method.addJavaDocLine(" *"); //$NON-NLS-1$

        sb.setLength(0);
        sb.append(" * @return the value of "); //$NON-NLS-1$
        sb.append(fieldConfiguration.getModelConfiguration().getFullyQualifiedTableText());
        sb.append('.');
        sb.append(fieldConfiguration.getColumnName());
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addGetterComment(Method method,
                                        FieldConfiguration fieldConfiguration) {
        addGetterComment(method, fieldConfiguration, null);
    }

    public static void addSetterComment(Method method,
                                        FieldConfiguration fieldConfiguration,
                                        String[] commoneLines) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * "); //$NON-NLS-1$
        if (!StringUtil.isBLank(fieldConfiguration.getComment())) {
            sb.append(fieldConfiguration.getComment());
        } else if (!StringUtil.isBLank(fieldConfiguration.getFieldLabel())) {
            sb.append(fieldConfiguration.getFieldLabel());
        } else {
            sb.append(fieldConfiguration.getColumnName());
        }
        method.addJavaDocLine(sb.toString());
        sb.setLength(0);

        addCommentLine(method, sb, commoneLines);

        method.addJavaDocLine(" *"); //$NON-NLS-1$

        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param "); //$NON-NLS-1$
        sb.append(parm.getName());
        sb.append(" the value for "); //$NON-NLS-1$
        sb.append(fieldConfiguration.getModelConfiguration().getFullyQualifiedTableText());
        sb.append('.');
        sb.append(fieldConfiguration.getColumnName());
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public static void addSetterComment(Method method,
                                        FieldConfiguration fieldConfiguration) {
        addSetterComment(method, fieldConfiguration, null);
    }

    public static void addClassComment(InnerClass innerClass,
                                ModelConfiguration modelConfiguration,
                                boolean markAsDoNotDelete) {

        if (isSuppressedAllComments()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass
                .addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(modelConfiguration.getFullyQualifiedTableText());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    private static boolean isSuppressedAllComments() {
        Configuration configuration = Context.getCurrConfiguration();
        if (configuration == null) {
            return false;
        }
        return configuration.isSuppressedAllComments();
    }
}
