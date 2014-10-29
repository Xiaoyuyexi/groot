package com.groot.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groot.module.domain.GrootSystemConfig;
import com.web.bo.AjaxMessage;

@Service
public class SystemConfigService {
	@Autowired
	private LookupCdeService lookupCdeService;
	
	@Autowired
	private ConfigService configService;
	
	public String getSystemBooleanValue(String code){
		String value = getSystemValue(code);
		if("1".equals(value)){
			return "true";
		}else{
			return "false";
		}
	}
	
	public String getSystemValue(String code){
		GrootSystemConfig config = configService.getSystemConfig(code);
		if(null != config){
		    return config.getConfigValue();
    	 }
		return "";
	}
	
	/**
	 * 校验验证码图片
	 * 
	 * @param session
	 * @param kaptcha
	 * @return
	 * @throws Exception
	 */
	public boolean validateKaptchaImage(HttpSession session, String kaptcha,
			AjaxMessage message) throws Exception {
		//false that the validater needn't show,return true mean is pass.
		if ("false".equals(getSystemBooleanValue("10000003"))) {
			return true;
		}
		String kaptchaExpected = (String) session
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		try {
			long start = (Long) session.getAttribute("TIMEOUT");
			long end = System.currentTimeMillis();
			if (end - start > 1000 * 60) {
				message.setCode("10");
				message.setValue("验证码超时");
				return false;
			}
		} catch (java.lang.NullPointerException e) {
			return false;
		}
		if (null != kaptcha && !kaptcha.equals(kaptchaExpected)) {
			message.setCode("11");
			message.setValue("请输入正确的验证码");
			return false;
		}
		return true;
	}
}
