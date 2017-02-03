/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.generator.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;

import java.sql.Types;

/**
 * Created by PanYunFeng on 2016/4/17.
 */
public class JdbcTypeTranslator extends JavaTypeResolverDefaultImpl {

    private static final JdbcTypeTranslator translator = new JdbcTypeTranslator();

    private JdbcTypeTranslator() {
    }

    public static FullyQualifiedJavaType getJavaType(String jdbcTypeName) {
        if (jdbcTypeName == null || jdbcTypeName.trim().length() == 0) {
            throw new IllegalArgumentException("JdbcTypeName is null");
        }

        int jdbcType = JdbcTypeNameTranslator.getJdbcType(jdbcTypeName.trim().toUpperCase());
        if (jdbcType == Types.OTHER) {
            if ("int".equalsIgnoreCase(jdbcTypeName)) {
                jdbcType = Types.INTEGER;
            } else if ("datetime".equalsIgnoreCase(jdbcTypeName)) {
                jdbcType = Types.DATE;
            }
        }
        return translator.typeMap.get(jdbcType).getFullyQualifiedJavaType();
    }

    public static int getJdbcType(String jdbcTypeName) {
        int jdbcType = JdbcTypeNameTranslator.getJdbcType(jdbcTypeName.trim().toUpperCase());
        if (jdbcType == Types.OTHER) {
            if ("int".equalsIgnoreCase(jdbcTypeName)) {
                jdbcType = Types.INTEGER;
            } else if ("datetime".equalsIgnoreCase(jdbcTypeName)) {
                jdbcType = Types.TIMESTAMP;
            }
        }
        return jdbcType;
    }
}
