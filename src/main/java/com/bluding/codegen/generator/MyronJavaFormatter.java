/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator;

import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.Context;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class MyronJavaFormatter implements JavaFormatter {

    public void setContext(Context context) {
    }

    public String getFormattedContent(CompilationUnit compilationUnit) {
        return compilationUnit.getFormattedContent();
    }
}
