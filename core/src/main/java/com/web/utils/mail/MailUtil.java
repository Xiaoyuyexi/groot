package com.web.utils.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;

import com.web.bo.Mail;

public class MailUtil {

	//private static ResourceBundle resource = ResourceBundle.getBundle("email");
	
	private static Mail mailArgs = null;
	private static String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	public static void init(Mail args){
		if(null == mailArgs)
		mailArgs = args;
	}
	
	public static boolean sendEmail(Mail mail) throws Exception {
		if(mail == null){
			return false;
		}
		if(StringUtils.isBlank(mail.getAddressee())){
			return false;
		}
		if(!mail.getAddressee().matches(reg)){
			return false;
		}
		mail = checkMail(mail);
		
		// 设置session,和邮件服务器进行通讯。
		Session session = getSession(mail);
		MimeMessage message = new MimeMessage(session);
		// message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
		Address from_address = new InternetAddress(mail.getSenter(), "");
		message.setFrom(from_address); // 设置邮件发送者的地址
		message.setSubject(mail.getSubject()); // 设置邮件主题
		message.setText(mail.getMessage()); // 设置邮件正文
		message.setHeader("", ""); // 设置邮件标题
		message.setSentDate(new Date()); // 设置邮件发送日期
		Address toAddress = new InternetAddress(mail.getAddressee()); // 设置邮件接收方的地址
		message.addRecipient(Message.RecipientType.TO, toAddress);
		Transport.send(message); // 发送邮件
		return true;
	}
	
	public static boolean sendEmailWithAttachment(Mail mail) throws Exception{
		if(mail == null){
			return false;
		}
		mail = checkMail(mail);
		
        Session session = getSession(mail);
        Transport trans = null;    
        Message message = new MimeMessage(session);   
        Address from_address = new InternetAddress(mail.getSenter(), "");
        message.setFrom(from_address);  
        message.setSubject(mail.getSubject());  
        InternetAddress[] address = {new InternetAddress(mail.getAddressee())};  
        message.setRecipients(Message.RecipientType.TO,address);  
        Multipart mp = new MimeMultipart();  
        MimeBodyPart mbp = new MimeBodyPart();  
        mbp.setContent(mail.getMessage(), "text/html;charset=gb2312");  
        mp.addBodyPart(mbp);
        if(mail.getFileArray() != null){
        	int length = mail.getFileArray().length;
        	if( length > 0){//有附件  
        		for (int i = 0; i < length; i++) {
        			File file = mail.getFileArray()[i];
        			mbp=new MimeBodyPart();  
        			FileDataSource fds=new FileDataSource(file); //得到数据源  
        			mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
        			mbp.setFileName(MimeUtility.encodeText(fds.getName()));  //得到文件名同样至入BodyPart  
        			mp.addBodyPart(mbp);
        		}
        	}   
        }
        message.setContent(mp); //Multipart加入到信件  
        message.setSentDate(new Date());     //设置信件头的发送日期  
        //发送信件  
        message.saveChanges();   
        trans = session.getTransport("smtp");  
		trans.connect(mail.getSenterHost(), mail.getSenterUserName(), mail.getSenterPasword());  
        trans.sendMessage(message, message.getAllRecipients());  
        trans.close();  
             
		return true;
	}
	
	public static Mail checkMail(Mail mail){
			mail.setSenter(mailArgs.getSenter());
			mail.setSenterHost(mailArgs.getSenterHost());
			mail.setSenterUserName(mailArgs.getSenterUserName());
			mail.setSenterPasword(mailArgs.getSenterPasword());
		return mail;
	}
	public static Session getSession(final Mail mail){
		Properties props = new Properties() ;  
        props.put("mail.smtp.host", mail.getSenterHost());  
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props,  
                new Authenticator() {  
                    public PasswordAuthentication getPasswordAuthentication() {  
                        return new PasswordAuthentication(mail.getSenterUserName(), mail.getSenterPasword());  
                    }  
                });  
        return session;
	}
	
	public static void main(String[] args){
//		Mail mail = new Mail();
//		mail.setAddressee("hanyu@hkrt.cn");
//		mail.setMessage("海科融通");
//		mail.setSubject("测试邮件");
//		try {
//			sendEmail(mail);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

		String email = "sxhsfasdfz163.com";
		boolean isValidaEmail = email.matches(reg);
				
		System.out.println(email.matches(reg));
	}
	
}
