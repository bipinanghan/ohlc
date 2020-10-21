package com.upstox.ohlc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class is used to get all spring beans
 * @author Bipin Anghan
 *
 */
public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext appCxt;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCxt = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> claz) throws BeansException {
		return (T) appCxt.getAutowireCapableBeanFactory().getBean(beanName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> claz) throws BeansException {
		return appCxt.getAutowireCapableBeanFactory().getBean(claz);
	}

}
