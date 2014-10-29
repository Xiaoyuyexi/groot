package com.groot.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.groot.busidao.SelectDAO;
import com.groot.interceptor.Listener;
import com.groot.module.domain.GrootSystemLookupCde;
public class LookupForKeyTagUtils extends TagSupport{
	public SelectDAO selectDao;
	
	private static final long serialVersionUID = 1L;
	private String typeCode;
	private String cdeKey;
	public String getCdeKey() {
		return cdeKey;
	}
	public void setCdeKey(String cdeKey) {
		this.cdeKey = cdeKey;
	}
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();  
		try { 
			if(typeCode==null||"".equals(typeCode)||cdeKey==null||"".equals(cdeKey)){
				out.print("");
				return EVAL_PAGE; 
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeCode", typeCode);
			map.put("cdeCode", cdeKey);
			
			selectDao = (SelectDAO)Listener.getApplicationContext().getBean("selectDAO");


			GrootSystemLookupCde cde= (GrootSystemLookupCde) selectDao.object("SYSTEM.getLookUpCdeList", map);
			if(cde==null){
				 out.print(cdeKey); 
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
	
}
