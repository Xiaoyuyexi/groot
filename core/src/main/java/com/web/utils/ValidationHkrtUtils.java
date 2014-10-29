package com.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验类
 * @author ZhaoDaping
 *
 */
public class ValidationHkrtUtils {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");//合法Email正则表达式
	private static final Pattern PHONE_PATTERN = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
	private static final Pattern CREDENTIAL_PATTERN = Pattern.compile("/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9][0-9]*");
	public static boolean validateEmail(String email) {//Email合法性校验
		Matcher m = EMAIL_PATTERN.matcher(email); // 通过正则表达式校验Email格式
		if (!m.matches()) {
			return false;
			}
		return true;
	}
	public static boolean validatePhone(String phone) {
		Matcher m = PHONE_PATTERN.matcher(phone); 
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	public static boolean validateLinkPhone(String linkPhone) {
		if(linkPhone.length()>11){
			return false;
		}
		Matcher m = NUMBER_PATTERN.matcher(linkPhone); 
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	public static boolean validateCredentialCode(String credentialCode) {
		Matcher m = CREDENTIAL_PATTERN.matcher(credentialCode); 
		if (!m.matches()) {
			return false;
		}
		return true;
	}
	public static boolean validateBankAccount(String bankAccount) {
		Matcher m = NUMBER_PATTERN.matcher(bankAccount); 
		if (!m.matches()) {
			return false;
		}
		return true;
	}
}
