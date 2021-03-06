/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator;

import com.bluding.codegen.context.model.ModelConfiguration;
import com.bluding.codegen.util.StringUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public abstract class AbstractGenerator {

    protected ModelConfiguration modelConfiguration;

    public AbstractGenerator(ModelConfiguration modelConfiguration) {
        this.modelConfiguration = modelConfiguration;
    }

    protected abstract List<GeneratedJavaFile> generate();


    /**
     * Writes, or overwrites, the contents of the specified file
     */
    public void generateAndwriteToFile() {
        List<GeneratedJavaFile> gjfs = generate();
        if (gjfs == null || gjfs.size() == 0) {
            return;
        }
        for (GeneratedJavaFile gjf : gjfs) {
            writeToFile(gjf);
        }
    }

    private void writeToFile(GeneratedJavaFile gjf) {
        File javaFile = null;
        try {
            File saveDir = new DefaultShellCallback(false).getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
            javaFile = new File(saveDir, gjf.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(javaFile, false);
            OutputStreamWriter osw;
            if (gjf.getFileEncoding() == null) {
                osw = new OutputStreamWriter(fos);
            } else {
                osw = new OutputStreamWriter(fos, gjf.getFileEncoding());
            }
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(gjf.getFormattedContent());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void addAnnotation(Object clazz, String[] annotationClassFullyQualifiedNameList) {
        if (annotationClassFullyQualifiedNameList == null || annotationClassFullyQualifiedNameList.length == 0) {
            return;
        }
        FullyQualifiedJavaType annotationClassType;
        for (String annotationClassFullyQualifiedName : annotationClassFullyQualifiedNameList) {
            annotationClassType = new FullyQualifiedJavaType(annotationClassFullyQualifiedName);
            addAnnotation(clazz, annotationClassType);
        }
    }

    public void addAnnotation(Object clazz, List<FullyQualifiedJavaType> annotationClassTypeList) {
        if (annotationClassTypeList == null || annotationClassTypeList.isEmpty()) {
            return;
        }
        annotationClassTypeList.forEach(annotationClassType -> {
            addAnnotation(clazz, annotationClassType);
        });
    }

    public void addAnnotation(Object clazz, String annotationClassFullyQualifiedName) {
        if (StringUtil.isBLank(annotationClassFullyQualifiedName)) {
            return;
        }
        FullyQualifiedJavaType annotationClassType = new FullyQualifiedJavaType(annotationClassFullyQualifiedName);
        addAnnotation(clazz, annotationClassType);
    }

    public void addAnnotation(Object clazz, FullyQualifiedJavaType annotationClassType) {
        if (annotationClassType == null) {
            return;
        }
        if (!(clazz instanceof JavaElement)) {
            return;
        }
        if (!(clazz instanceof CompilationUnit)) {
            return;
        }
        ((CompilationUnit)clazz).addImportedType(annotationClassType);
        ((JavaElement)clazz).addAnnotation("@" + annotationClassType.getShortName());
    }
}
