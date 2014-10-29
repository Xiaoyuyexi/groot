package com.groot.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.groot.service.SystemConfigService;
import com.web.bo.AjaxMessage;

@Controller
public class CheckCodeController {
	@Autowired
    private Producer captchaProducer = null;  
  
	@Autowired
	private SystemConfigService systemConfigService;
	
    @RequestMapping(value = "/getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpSession session = request.getSession();
        /*String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
        System.out.println("******************验证码是: " + code + "******************");  */
          
        response.setDateHeader("Expires", 0);  
          
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
          
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
          
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
          
        // return a jpeg  
        response.setContentType("image/jpeg");  
          
        // create the text for the image  
        String capText = captchaProducer.createText();  
          
        // store the text in the session  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        session.setAttribute("TIMEOUT", System.currentTimeMillis());
          
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
          
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
    }
    
    /**
     * 校验验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/validateKaptchaImage")
    @ResponseBody
    public Object validateKaptchaImage(HttpSession session,String kaptcha) {  
    	AjaxMessage message = new AjaxMessage();
    	message.setCode("00");
    	
    	try {
    		systemConfigService.validateKaptchaImage(session,kaptcha,message);
		} catch (Exception e) {
			message.setValue("验证码异常");
			message.setCode("20");
		}
		return message;
    }
}
