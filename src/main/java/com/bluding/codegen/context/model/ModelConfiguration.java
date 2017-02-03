package com.bluding.codegen.context.model;

import com.bluding.codegen.util.StringUtil;
import lombok.Data;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;

/**
 * Created by myron.pan on 2017/01/29.
 */
@Data
public class ModelConfiguration {

    private Configuration configuration;

    public ModelConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    private String catalog;
    private String schema;
    private String tableName;
    private String keyColumnName;
    private String alias;
    private String entityName;
    private String entityDesc;
    private String moduleDesc;
    private String requestMapping;
    private String pageLocation;
    private List<FieldConfiguration> fields;

    public String getFullyQualifiedTableText() {
        StringBuilder sb = new StringBuilder();

        if (catalog != null && catalog.length() > 0) {
            sb.append(catalog);
            sb.append(".");
        }

        if (schema != null && schema.length() > 0) {
            sb.append(schema);
            sb.append(".");
        } else {
            if (sb.length() > 0) {
                sb.append(".");
            }
        }

        sb.append(tableName);

        return sb.toString();
    }

    public String getCamelClassName() {
        return StringUtil.initialsToLowerCase(entityName);
    }

    public String getCamelKeyColName() {
        return JavaBeansUtil.getCamelCaseString(keyColumnName, false);
    }

    public String getUpperCamelKeyColName() {
        return JavaBeansUtil.getCamelCaseString(keyColumnName, true);
    }

}
