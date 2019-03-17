package com.ninox.focus.view.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringContextHelper {
	private static ApplicationContext context;
	
	public SpringContextHelper() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	public static Object getBean(final String beanRef) {
		return context.getBean(beanRef);
	}
}
