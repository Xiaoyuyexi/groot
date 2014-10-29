package com.web.utils.lang;

import java.math.BigDecimal;

import org.apache.commons.beanutils.Converter;

public class BigDecimalConverter implements Converter{
	 public Object convert(Class arg0, Object arg1) {
		 BigDecimal p = (BigDecimal)arg1;
	        return p;
	    }
}
