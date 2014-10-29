package com.web.utils.lang;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;

public class HkrtBeanUtils extends BeanUtils{
	private HkrtBeanUtils() {
	  }

	  static {
	    //注册sql.date的转换器，允许源目标的date类型的值允许为空
	    ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
	    ConvertUtils.register(new DateConverter(), java.util.Date.class);
	    ConvertUtils.register(new BigDecimalConverter(), java.math.BigDecimal.class);
	  }

	  public static void copyProperties(Object target, Object source) throws
	      InvocationTargetException, IllegalAccessException {
		  BeanUtils.copyProperties(target, source);

	  }
}
