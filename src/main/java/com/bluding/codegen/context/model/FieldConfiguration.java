/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.context.model;

import com.bluding.codegen.generator.util.JdbcTypeTranslator;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;

/**
 * 领域中的字段
 *
 * Created by PanYunFeng on 2016/4/16.
 */
@Data
public class FieldConfiguration {
//    // 字段对应的表列类型
//    private String columnType;
//    // 列宽
//    private String columnLength;
//    private String fieldCNName;
//    private String fieldComment;
//    // 字段columnType对应的javaType，不配置则采用默认规则。
//    private String fieldJavaType;
//    // 字段长度校验， 默认列宽
//    private String fieldMaxLength;
//    // 是否必选，会加入判断 是否为空，
//    private boolean isRequired;
//    // 是否出现在实体类中
//    private boolean isShowInEntity;
//    private boolean isShowInList;
//    private boolean isShowInAddForm;
//    private boolean isShowInUpdateForm;

    // 字段对应的表列名
    private String columnName;
    private String jdbcType;
    private String jdbcLength;
    // 列和类字段的映射名称，默认列名转驼峰;
    private String fieldName;
    private String javaType;
    // 字段中文名，用于表单、列表字段名称显示，默认列备注或列名称
    private String comment;
    // 字段注释，默认fieldCNName;
    private String fieldLabel;
    private boolean isBaseAttr = false;
    private boolean required = false;

    private boolean showInEntity = true;
    // 是否出现在页面列表中
    private boolean showInList = true;
    // 是否出现在添加雷彪中；
    private boolean showInAddForm = true;
    // 是否出现在修改列表中；
    private boolean showInUpdateForm = true;

    private ModelConfiguration modelConfiguration;

    public FieldConfiguration(ModelConfiguration modelConfiguration) {
        this.modelConfiguration = modelConfiguration;
    }

    public ModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

    public String getLabelText() {
        return StringUtils.isBlank(fieldLabel) ? columnName : fieldLabel;
    }

    public boolean isShowInEntity() {
        return showInEntity;
    }

    public String getColumnName() {
        if (columnName == null) {
            return null;
        }
        return columnName.toLowerCase();
    }

    public boolean isStringColumn() {
        return false;
    }

    public String getMybatisJdbcType() {
        int iJdbcType = JdbcTypeTranslator.getJdbcType(jdbcType);
        return JdbcTypeNameTranslator.getJdbcTypeName(iJdbcType);
    }

//    public Boolean getShowInEntity() {
//        return showInEntity;
//    }
//
//    public Boolean getShowInList() {
//        return showInList;
//    }
//
//    public Boolean getShowInAddForm() {
//        return showInAddForm;
//    }
//
//    public Boolean getShowInUpdateForm() {
//        return showInUpdateForm;
//    }
}
