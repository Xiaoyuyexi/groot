package com.groot.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.groot.busidao.SelectDAO;
import com.groot.interceptor.Listener;
import com.groot.module.domain.GrootSystemLookupCde;
public class LookupTagUtils extends TagSupport{
	public SelectDAO selectDao;
	
	private static final long serialVersionUID = 1L;
	private String typeCode;
	private String cdeCode;
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();  
		try { 
			if(typeCode==null||"".equals(typeCode)||cdeCode==null||"".equals(cdeCode)){
				out.print("");
				return EVAL_PAGE; 
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeCode", typeCode);
			map.put("cdeCode", cdeCode);
			
			selectDao = (SelectDAO)Listener.getApplicationContext().getBean("selectDAO");


			GrootSystemLookupCde cde= (GrootSystemLookupCde) selectDao.object("SYSTEM.getLookUpCdeList", map);
			if(cde==null){
				 out.print(cdeCode); 
			}else{
				 out.print(cde.getCdeName()); 
			}
		} catch (Exception e) { 
			throw new JspException(e);
		} 
		return EVAL_PAGE; 
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getCdeCode() {
		return cdeCode;
	}
	public void setCdeCode(String cdeCode) {
		this.cdeCode = cdeCode;
	}
}
