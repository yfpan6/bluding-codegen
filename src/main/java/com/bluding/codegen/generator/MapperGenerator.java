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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class MapperGenerator extends AbstractGenerator {

    public MapperGenerator(ModelConfiguration modelConfiguration) {
        super(modelConfiguration);
    }

    public List<GeneratedJavaFile> generate() {
        Configuration configuration = modelConfiguration.getConfiguration();
        String classFullName = configuration.getMapperPackage() + "."
                + modelConfiguration.getEntityName() + "Mapper";
        Interface clazz = new Interface(classFullName);
        clazz.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType baseMapper = new FullyQualifiedJavaType(configuration.getBaseMapper());
        clazz.addImportedType(baseMapper);
        clazz.addSuperInterface(baseMapper);

        String annotationCfg = "org.apache.ibatis.annotations.Mapper";
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

        MyronCommentGenerator.addJavaComment(clazz, modelConfiguration);

        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        list.add(JavaBeanUtil.getGeneratedJavaFile(configuration, clazz));
        return list;
    }
}
