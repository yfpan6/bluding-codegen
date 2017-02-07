package com.bluding.codegen.freemarker;

public enum UITemplateFile {	
	
	JSP_MAIN("m.ftl", ".jsp"),
	JS_MAIN("mjs.ftl", ".js"),
	MAPPER("mapper.ftl", ".xml"),
	CONTROLLER("controller.ftl", ".java"),
	VIEW_MAIN("view_main.ftl", ".html"),
	VIEW_LIST("view_list.ftl", ".html"),
	VIEW_FORM_ADD("view_formAdd.ftl", ".html"),
	VIEW_FORM_UPDATE("view_formUpdate.ftl", ".html");
//	, 
//	JS_ADD_FORM("a.ftl", "AddForm.js"), 
//	JS_UPDATE_FORM("u.ftl", "UpdateForm.js"), 
//	JS_ENTITY("e.ftl", ".js");
	
	
//	JS_MAIN("view_main.ftl", "List.js"),
//	JS_ADD_FORM("addform.ftl", "AddForm.js"), 
//	JS_UPDATE_FORM("updateform.ftl", "UpdateForm.js"), 
//	JS_ENTITY("entity.ftl", ".js");
	
	private String fileName;
	private String outFile;
	
	private UITemplateFile(String fileName, String outFile) {
		this.fileName = fileName;
		this.outFile = outFile;
	}
	
	public String getValue() {
		return fileName;
	}
	
	public String getOutFile() {
		return outFile;
	}
}
