/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator;

import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.FieldConfiguration;
import com.bluding.codegen.context.model.ModelConfiguration;
import com.bluding.codegen.generator.util.JavaBeanUtil;
import com.bluding.codegen.generator.util.MyronCommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class EntityGenerator extends AbstractGenerator {

    public EntityGenerator(ModelConfiguration modelConfiguration) {
        super(modelConfiguration);
    }

    public List<GeneratedJavaFile> generate() {
        Configuration configuration = modelConfiguration.getConfiguration();
        String classFullName = JavaBeanUtil.getFullClassName(
                configuration.getEntityPackage(),
                modelConfiguration.getEntityName());
        TopLevelClass clazz = new TopLevelClass(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(configuration.getBaseEntity());
        clazz.addImportedType(configuration.getBaseEntity());

        MyronCommentGenerator.addClassComment(clazz, modelConfiguration);

        List<FieldConfiguration> fields = modelConfiguration.getFields();
        Field colNameField = null;
        for (FieldConfiguration field : fields) {
            if (!field.isShowInEntity()) {
                continue;
            }
            colNameField = new Field();
            colNameField.setType(FullyQualifiedJavaType.getStringInstance());
            colNameField.setVisibility(JavaVisibility.PUBLIC);
            colNameField.setStatic(true);
            colNameField.setFinal(true);
            colNameField.setName(field.getColumnName().toUpperCase());
            colNameField.setInitializationString("\"" + field.getColumnName() + "\"");
            clazz.addField(colNameField);
        }


        Field field = null;
        Method getter = null;
        Method setter = null;
        for (FieldConfiguration domainField : fields) {
            if (!domainField.isShowInEntity()) {
                continue;
            }
            field = JavaBeanUtil.getJavaBeansField(domainField);
            if (field.getType().equals(FullyQualifiedJavaType.getDateInstance())) {
                clazz.addImportedType(field.getType());
            }
            getter = JavaBeanUtil.getJavaBeansGetter(domainField);
            setter = JavaBeanUtil.getJavaBeansSetter(configuration, domainField);

            clazz.addField(field);
            MyronCommentGenerator.addFieldComment(field, domainField);
            clazz.addMethod(getter);
            clazz.addMethod(setter);
        }

        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(JavaBeanUtil.getGeneratedJavaFile(configuration, clazz));
        return list;
    }
}
