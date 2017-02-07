package com.bluding.codegen.context.model;

import lombok.Data;

/**
 * Created by myron.pan on 2017/01/29.
 */
@Data
public class Configuration {

    private String id;
    private String configGroup;
    private String jdbcUrl;
    private String driverCLass;
    private String username;
    private String password;
    private String srcPath;
    private String basePackage;
    private String author;
    private String copyright;
    private String baseEntity;
    private String baseService;
    private String baseServiceImpl;
    private String baseMapper;

    private String controllerPackage;
    private String entityPackage;
    private String servicePackage;
    private String serviceImplPackage;
    private String mapperPackage;

    private String serviceAnnotationsClassName;

    private String databaseName;

    // base entity 中 属性对应的 列名
    private String baseFields;

    private String databasePrefix;

    private String fileEncoding = "UTF-8";
    private boolean trimStringsEnabled;
    private boolean suppressedAllComments;

    public String toString() {
        return configGroup;
    }
}
