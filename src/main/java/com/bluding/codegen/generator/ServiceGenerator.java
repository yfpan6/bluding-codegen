/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator;

import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.ModelConfiguration;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.*;
import com.bluding.codegen.generator.util.MyronCommentGenerator;
import com.bluding.codegen.generator.util.JavaBeanUtil;
import com.bluding.codegen.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class ServiceGenerator extends AbstractGenerator {

    public ServiceGenerator(ModelConfiguration modelConfiguration) {
        super(modelConfiguration);
    }

    public List<GeneratedJavaFile> generate() {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(generateInterface());
        list.add(generateImpl());
        return list;
    }

    private GeneratedJavaFile generateInterface() {
        Configuration configuration = modelConfiguration.getConfiguration();
        String interfaceFullName = configuration.getServicePackage() + "."
                + modelConfiguration.getEntityName() + "Service";
        Interface clazz = new Interface(interfaceFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.addImportedType(new FullyQualifiedJavaType(configuration.getBaseService()));
        clazz.addSuperInterface(new FullyQualifiedJavaType(configuration.getBaseService()));

        return JavaBeanUtil.getGeneratedJavaFile(configuration, clazz);
    }

    private GeneratedJavaFile generateImpl() {
        Configuration configuration = modelConfiguration.getConfiguration();
        String classFullName = configuration.getServicePackage() + ".impl."
                + modelConfiguration.getEntityName() + "ServiceImpl";
        TopLevelClass clazz = new TopLevelClass(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);

        clazz.addImportedType(configuration.getBaseServiceImpl());
        clazz.setSuperClass(configuration.getBaseServiceImpl());

        FullyQualifiedJavaType interfaze = new FullyQualifiedJavaType(configuration.getServicePackage()
            + "."+ modelConfiguration.getEntityName() + "Service"
        );
        clazz.addImportedType(interfaze);
        clazz.addSuperInterface(interfaze);

        FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(
                JavaBeanUtil.getFullClassName(
                        configuration.getMapperPackage(),
                        modelConfiguration.getEntityName() + "Mapper"
                ));
        clazz.addImportedType(mapper);

        String annotationCfg = configuration.getServiceAnnotationsClassName();
        if (annotationCfg != null && annotationCfg.trim().length() >= 0) {
            String[] annotations = annotationCfg.split(",");
            if (annotations.length > 0) {
                FullyQualifiedJavaType annotationClassType = null;
                for (String annotationClass : annotations) {
                    if (annotationClass == null || annotationClass.trim().length() == 0) {
                        continue;
                    }
                    annotationClassType = new FullyQualifiedJavaType(annotationClass);
                    clazz.addImportedType(annotationClassType);
                    clazz.addAnnotation("@" + annotationClassType.getShortName());
                }
            }
        }

        MyronCommentGenerator.addClassComment(clazz, modelConfiguration);

        String mapperFieldName = StringUtil.initialsToLowerCase(mapper.getShortName());
        Field mapperField = JavaBeanUtil.getField(mapperFieldName,
                mapper, JavaVisibility.PRIVATE
        );
        clazz.addImportedType("javax.annotation.Resource");
        mapperField.addAnnotation("@Resource");
        clazz.addField(mapperField);

        Method method = JavaBeanUtil.getMethod("getMapper", mapper, JavaVisibility.PUBLIC);
        method.addBodyLine("return " + mapperFieldName + ";");
        clazz.addMethod(method);

        return JavaBeanUtil.getGeneratedJavaFile(configuration, clazz);
    }
}
