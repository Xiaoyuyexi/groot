package com.web.utils.lang;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter{
	 public Object convert(Class arg0, Object arg1) {
	        Date p = (Date)arg1;
	        return p;
	    }
}
