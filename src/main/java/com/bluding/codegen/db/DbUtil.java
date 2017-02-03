/*
 * Copyright 2016-2017 the original author or authors.
 */
package com.bluding.codegen.db;

import com.bluding.codegen.context.Context;
import com.bluding.codegen.context.model.Configuration;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by PanYunFeng on 2016/4/15.
 */
public class DbUtil {

    public static final Connection getConn(Configuration configuration) {
        if (configuration == null) {
            return null;
        }
        DbUtils.loadDriver(configuration.getDriverCLass());
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    configuration.getJdbcUrl(),
                    configuration.getUsername(),
                    configuration.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
