package com.taotao.item.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreemarker {

	@Test
	public void testBuildHtml() throws Exception{
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/springmvc-freemarker.xml");
		
		FreeMarkerConfigurer config =  (FreeMarkerConfigurer)applicationContext.getBean("freeMarkerConfigurer");
		
		Configuration configuration = config.getConfiguration();
		
		Template template = configuration.getTemplate("hello.ftl");
		
		Map data = new HashMap<>();
		data.put("hello", "你们12好！");
		
		Writer out = new FileWriter(new File("F:/taotao商城综合项目dubbo版/taotao/hello1.html"));
		
		template.process(data, out);
		out.close();
	}
	
	@Test
	public void testFree2() throws Exception{
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		configuration.setDirectoryForTemplateLoading(new File("../taotao-item-web/src/main/webapp/WEB-INF/ftl"));
	
		configuration.setDefaultEncoding("UTF-8");
		
		Template template = configuration.getTemplate("hello.ftl");
		
		Map data = new HashMap<>();
		data.put("hello", "你们好！");
		
		Writer out = new FileWriter(new File("F:/taotao商城综合项目dubbo版/taotao/hello.html"));
		
		template.process(data, out);
		out.close();
	}
}
