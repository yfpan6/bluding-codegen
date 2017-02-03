package com.bluding.codegen.db;

import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.generator.util.JdbcTypeTranslator;
import com.bluding.codegen.ui.vo.TableRowVO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.dbutils.DbUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableHandler {
	
	public static final String DEFAULT_SELECTED = "--- 选张表吧 ---";
	
	public static final String tablesSql = "select " +
			"TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT " +
			"from information_schema.TABLES where TABLE_SCHEMA = ?";

	public static final String columnsSql = "select " +
			"TABLE_CATALOG, TABLE_SCHEMA, IS_NULLABLE, NUMERIC_SCALE, NUMERIC_PRECISION," +
			"COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_COMMENT " +
			"from information_schema.COLUMNS  "
			+ "where TABLE_SCHEMA = ?  and TABLE_NAME = ?";
	
	public static List<String> getTableNameList(Configuration configuration, String dbName) {
		List<String> nameList = new ArrayList<String>();
		//nameList.add(DEFAULT_SELECTED);
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn(configuration);
			stm = conn.prepareStatement(tablesSql);
			stm.setString(1, conn.getCatalog());
			rs = stm.executeQuery();

			while (rs.next()) {
				nameList.add(rs.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, stm, rs);
		}
		return nameList;
	}
	
	public static List<TableRowVO> getTableColumns4TCModel(Configuration configuration, String tableName) {
		List<TableRowVO> rowList = new ArrayList<TableRowVO>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn(configuration);
			stm = conn.prepareStatement(columnsSql);
			stm.setString(1, conn.getCatalog());
			stm.setString(2, tableName);
			rs = stm.executeQuery();
			TableRowVO row;
			while (rs.next()) {
				String colName = rs.getString("COLUMN_NAME");
				row = new TableRowVO();
				row.setColumnName(new SimpleStringProperty(colName));
				row.setFieldName(new SimpleStringProperty(JavaBeansUtil.getCamelCaseString(colName, false)));
				row.setJdbcType(new SimpleStringProperty(rs.getString("DATA_TYPE")));
				row.setJdbcLength(new SimpleStringProperty(rs.getString("CHARACTER_MAXIMUM_LENGTH")));
				row.setFieldLabel(new SimpleStringProperty(rs.getString("COLUMN_COMMENT")));
				row.setComment(new SimpleStringProperty(rs.getString("COLUMN_COMMENT")));
				row.setJavaType(new SimpleStringProperty(JdbcTypeTranslator
						.getJavaType(rs.getString("DATA_TYPE")).toString()));
				if (rs.getString("IS_NULLABLE") != null &&
						"YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"))) {
					row.setRequired(new SimpleBooleanProperty(false));
				} else {
					row.setRequired(new SimpleBooleanProperty(true));
				}
				if (rs.getString("NUMERIC_SCALE") != null) {
					row.setJdbcLength(new SimpleStringProperty(rs.getString("NUMERIC_PRECISION")));
				}
				if (isBaseField(configuration, colName)) {
					row.setShowInEntity(new SimpleBooleanProperty(false));
					row.setShowInList(new SimpleBooleanProperty(false));
					row.setShowInAddForm(new SimpleBooleanProperty(false));
					row.setShowInUpdateForm(new SimpleBooleanProperty(false));
				} else {
					row.setShowInEntity(new SimpleBooleanProperty(true));
					row.setShowInList(new SimpleBooleanProperty(true));
					row.setShowInAddForm(new SimpleBooleanProperty(true));
					row.setShowInUpdateForm(new SimpleBooleanProperty(true));
				}
				rowList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, stm, rs);
		}
		return rowList;
	}

	private static boolean isBaseField(Configuration configuration, String columnName) {
		if (columnName == null) {
			return false;
		}
		String baseFileds = configuration.getBaseFields();
		if (baseFileds == null || baseFileds.trim().length() == 0) {
			return false;
		}
		if (!baseFileds.startsWith("*")) {
			baseFileds = "*" + baseFileds;
		}
		if (!baseFileds.endsWith("*")) {
			baseFileds = baseFileds + "*";
		}
		configuration.setBaseFields(baseFileds);
		baseFileds = baseFileds.toLowerCase();
		if (baseFileds.indexOf("*" + columnName.toLowerCase() + "*") != -1) {
			return true;
		}
		return false;
	}
}
