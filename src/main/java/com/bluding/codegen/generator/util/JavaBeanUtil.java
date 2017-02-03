/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator.util;

import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.FieldConfiguration;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import com.bluding.codegen.generator.MyronJavaFormatter;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class JavaBeanUtil {

    public static Method getJavaBeansGetter(FieldConfiguration modelField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(modelField.getJavaType());
        String property = modelField.getFieldName();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(fieldJavaType);
        method.setName(JavaBeansUtil.getGetterMethodName(property, fieldJavaType));
        MyronCommentGenerator.addGetterComment(method, modelField);

        StringBuilder sb = new StringBuilder();
        sb.append("return "); //$NON-NLS-1$
        sb.append(property);
        sb.append(';');
        method.addBodyLine(sb.toString());

        return method;
    }

    public static Field getJavaBeansField(FieldConfiguration modelField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(modelField.getJavaType());
        String property = modelField.getFieldName();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fieldJavaType);
        field.setName(property);
        return field;
    }

    public static Method getJavaBeansSetter(Configuration configuration, FieldConfiguration modelField) {
        FullyQualifiedJavaType fieldJavaType = new FullyQualifiedJavaType(modelField.getJavaType());
        String property = modelField.getFieldName();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(JavaBeansUtil.getSetterMethodName(property));
        method.addParameter(new Parameter(fieldJavaType, property));
        MyronCommentGenerator.addSetterComment(method, modelField);

        StringBuilder sb = new StringBuilder();
        if (configuration.isTrimStringsEnabled()
                && modelField.isStringColumn()) {
            sb.append("this."); //$NON-NLS-1$
            sb.append(property);
            sb.append(" = "); //$NON-NLS-1$
            sb.append(property);
            sb.append(" == null ? null : "); //$NON-NLS-1$
            sb.append(property);
            sb.append(".trim();"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
        } else {
            sb.append("this."); //$NON-NLS-1$
            sb.append(property);
            sb.append(" = "); //$NON-NLS-1$
            sb.append(property);
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        return method;
    }

    public static final Field getField(String fieldName,
                                       FullyQualifiedJavaType javaType,
                                       JavaVisibility visibility) {
        Field field = new Field(fieldName, javaType);
        field.setVisibility(visibility);
        return field;
    }

    public static final Field getField(String fieldName,
                                       String javaType, JavaVisibility visibility) {
        return getField(fieldName, new FullyQualifiedJavaType(javaType), visibility);
    }

    public static final Method getMethod(String name,
            String returnType, JavaVisibility visibility) {
        Method method = new Method(name);
        method.setVisibility(visibility);
        method.setReturnType(new FullyQualifiedJavaType(returnType));
        return method;
    }

    public static final Method getMethod(String name,
            FullyQualifiedJavaType returnType, JavaVisibility visibility) {
        Method method = new Method(name);
        method.setVisibility(visibility);
        method.setReturnType(returnType);
        return method;
    }

    public static  GeneratedJavaFile getGeneratedJavaFile(Configuration configuration, CompilationUnit compilationUnit) {
        return  new GeneratedJavaFile(compilationUnit,
                configuration.getSrcPath(),
                configuration.getFileEncoding(),
                new MyronJavaFormatter());
    }

    public static final FullyQualifiedJavaType getFullType(String fullyQualifiedJavaType) {
        return new FullyQualifiedJavaType(fullyQualifiedJavaType);
    }

    public static final String getFullClassName(String packageName, String className) {
        if (packageName == null || packageName.trim().length() == 0) {
            return className;
        }
        packageName = packageName.trim();
        if (packageName.endsWith(".")) {
            return packageName + className;
        }
        return packageName + "." + className;
    }
}
