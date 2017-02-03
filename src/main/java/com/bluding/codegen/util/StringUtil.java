package com.bluding.codegen.util;

public class StringUtil {

	public static String initialsToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}


	public static String initialsToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	
	public static boolean isBLank(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static String getTableAlias(String tableName) {
		if (isBLank(tableName)) {
			return "";
		}
		String[] tempArr = tableName.split("_");
		StringBuilder sb = new StringBuilder();
		for (String t : tempArr) {
			sb.append(t.trim().charAt(0));
		}
		return sb.toString();
	}
}
