package com.web.utils.lang;

import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * 模板工具类
 * @author ZhaoDaping
 *
 */
public class VelocityUtil {

	public static String getContentByPath(String path,Object content)throws Exception {
		 /* first, get and initialize an engine */
		 	Properties p = new Properties();   
	        //设置输入输出编码类型。
	         p.setProperty(Velocity.INPUT_ENCODING, "UTF8");   
	         p.setProperty(Velocity.OUTPUT_ENCODING, "GBK");   
	         //这里加载类路径里的模板而不是文件系统路径里的模板   
	         p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");   
	        VelocityEngine ve = new VelocityEngine();
	        ve.init(p);
	        /* next, get the Template */
	        Template t = ve.getTemplate(path);
	        /* create a context and add data */
	        VelocityContext context = new VelocityContext();
	        
	        //加入String解析的工具包
	        context.put("su", new StringUtils());
	        //加入String解析的工具包
	        context.put("du", new DateFormatUtils());
	        context.put("dateUtils", new DateUtils());
	        context.put("now", new Date());
	        
	        context.put("mf", new MoneyUtils());
	        context.put("object", content);
	        /* now render the template into a StringWriter */
	        StringWriter writer = new StringWriter();
	        t.merge(context, writer);
	        /* show the World */
	        return writer.toString();
	 }
	
	public static String getContentByStringTemplate(String template,String name,Object content)throws Exception {
		 /* first, get and initialize an engine */
		 	Properties p = new Properties();   
	        //设置输入输出编码类型。
	         p.setProperty(Velocity.INPUT_ENCODING, "UTF8");   
	         p.setProperty(Velocity.OUTPUT_ENCODING, "GBK");   
	         //这里加载类路径里的模板而不是文件系统路径里的模板   
	         //通过String加载模板
	         p.setProperty(VelocityEngine.RESOURCE_LOADER, "string");
	         p.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
	         VelocityEngine ve = new VelocityEngine();
	         ve.init(p);
	        /* next, get the Template */
	        //创建资源库
	        StringResourceRepository repo = StringResourceLoader.getRepository();
	        //模板资源存放 资源库 中
	        repo.putStringResource(name, template);
	        
	        Template t = ve.getTemplate(name);
	        /* create a context and add data */
	        VelocityContext context = new VelocityContext();
	        context.put("object", content);
	        //加入String解析的工具包
	        context.put("su", new StringUtils());
	        //加入String解析的工具包
	        context.put("du", new DateFormatUtils());
	        context.put("dateUtils", new DateUtils());
	        context.put("now", new Date());
	        context.put("mf", new MoneyUtils());
	        /* now render the template into a StringWriter */
	        StringWriter writer = new StringWriter();
	        t.merge(context, writer);
	        /* show the World */
	        return writer.toString();
	 }

	public static String getContentByStringTemplate(String template,String name,Object content,VelocityContext context)throws Exception {
		 	Properties p = new Properties();   
	        //设置输入输出编码类型。
	         p.setProperty(Velocity.INPUT_ENCODING, "UTF8");   
	         p.setProperty(Velocity.OUTPUT_ENCODING, "GBK");   
	         //这里加载类路径里的模板而不是文件系统路径里的模板   
	         //通过String加载模板
	         p.setProperty(VelocityEngine.RESOURCE_LOADER, "string");
	         p.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
	         VelocityEngine ve = new VelocityEngine();
	         ve.init(p);
	        /* next, get the Template */
	        //创建资源库
	       // StringResourceRepository repo = StringResourceLoader.getRepository();
	        //模板资源存放 资源库 中
	        //repo.putStringResource(name, template);
	       // 
	        //Template t = ve.getTemplate(name);
	        /* create a context and add data */
	        if(context==null){
	        	context = new VelocityContext();
	        }
	        context.put("object", content);
	        //加入String解析的工具包
	        context.put("su", new StringUtils());
	        //加入String解析的工具包
	        context.put("du", new DateFormatUtils());
	        context.put("dateUtils", new DateUtils());
	        context.put("now", new Date());
	        context.put("mf", new MoneyUtils());
	        /* now render the template into a StringWriter */
	        //StringWriter writer = new StringWriter();
	       // t.merge(context, writer);
	        StringWriter writer = new StringWriter();  
	        ve.evaluate(context, writer, "", template);
	        /* show the World */
	        return writer.toString();
	 }
	
	public static void main(String[] args) {
		try {
	        System.out.println(new MoneyUtils().normalCommaFormat(22222323.123333));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

