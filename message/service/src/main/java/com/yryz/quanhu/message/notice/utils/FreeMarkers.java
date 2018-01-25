/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yryz.quanhu.message.notice.utils;

import com.yryz.common.context.Context;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarkers工具类
 * 
 * @author ThinkGem
 * @version 2013-01-15
 */
public class FreeMarkers {
	private static Logger logger = Logger.getLogger(FreeMarkers.class);

	public static String renderString(String templateString, Map<String, String> model) {
		try {
			StringWriter result = new StringWriter();
			Template t = new Template("name", new StringReader(templateString), new Configuration());
			t.process(model, result);
			return result.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String renderTemplate(Template template, Object model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static Configuration buildConfiguration(String directory) throws IOException {
		Configuration cfg = new Configuration();
		//Resource path = new DefaultResourceLoader().getResource(directory);
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
		cfg.setDirectoryForTemplateLoading(file);
		return cfg;
	}

	public static String createFile(String fileName, Map<String, String> model) throws IOException {
		String path = AdvertUtil.getRealPath();
		Configuration cfg = FreeMarkers.buildConfiguration(path);
		Template template = cfg.getTemplate(fileName + ".ftl", "utf-8");
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = myFmt.format(new Date());

		StringBuffer filePath = new StringBuffer();
		filePath.append(AdvertUtil.getRealPath());
		filePath.append(Context.getProperty("web.newPageUrl"));
		if(!new File(filePath.toString()).isDirectory())
			new File(filePath.toString()).mkdirs();
		filePath.append(fileName).append("_").append(date).append(".html");

		if (logger.isDebugEnabled())
			logger.debug("advert html filePath:" + filePath.toString());

		FileOutputStream newfile = new FileOutputStream(filePath.toString());
		OutputStreamWriter outStream = new OutputStreamWriter(newfile, "UTF-8");
		Writer out = new BufferedWriter(outStream);
		try {
			template.process(model, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
		return new StringBuffer(fileName).append("_").append(date).append(".html").toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> model = new HashMap<>();
		model.put("userName", "calvin");
		String result = FreeMarkers.renderString(
				"hello ${userName} ${r'${userName}'} <#assign number=0> <#list 1..5 as num> <#if number<3 > ${number}<#assign number=number+1> </#if></#list>${number}",
				model);
		System.out.println(result);
	}

}
