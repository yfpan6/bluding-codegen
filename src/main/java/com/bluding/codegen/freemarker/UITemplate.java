package com.bluding.codegen.freemarker;

import com.bluding.codegen.context.Context;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

public class UITemplate {
	
	public static final Configuration cfg;
	
	static {
		 cfg = new Configuration(); 
		 try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			cfg.setEncoding(Locale.CHINESE, "UTF-8");
			cfg.setDirectoryForTemplateLoading( 
					 new File(path + "template"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		 cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	public static Template getTemplate(UITemplateFile template) {
		try {
			return cfg.getTemplate(template.getValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void write(Map<String, Object> valMap, 
			UITemplateFile template) {
		write(valMap, template, (String) valMap.get("entityVar"), null);
	}
	
	public static void write(Map<String, Object> valMap,
			UITemplateFile template , String entityName, String toDir) {
		if (toDir == null || toDir.trim().length() == 0) {
			toDir = Context.getCurrConfigOrEmptyConfig().getSrcPath();
		}
		if (!toDir.endsWith("/")) {
			toDir = toDir + "/";
		}
		Template temp = getTemplate(template);
		Writer out = null;
		try {
			File dir = new File(toDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String toFile = entityName + template.getOutFile();
			out = new OutputStreamWriter(new FileOutputStream(toDir + toFile), "UTF-8");
			temp.process(valMap, out);
			
			out.flush(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Template temp = getTemplate(UITemplateFile.JS_MAIN); 
		 /* 创建数据模型 */ 
		 Map<String, Object> root = new HashMap<String, Object>(); 
		 root.put("user", "Big Joe");
		 root.put("list", null);
		 /* 将模板和数据模型合并 */ 
		 Writer out = new OutputStreamWriter(System.out); 
		 try {
			 temp.process(root, out);
			 out.flush(); 
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
}
