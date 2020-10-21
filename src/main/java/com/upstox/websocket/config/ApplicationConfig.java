package com.upstox.websocket.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.upstox.ohlc.utils.SpringBeanUtil;
import com.upstox.websocket.service.ClientSessionManager;
import com.upstox.websocket.servlet.StockServlet;

/**
 * Establish connection
 * @author Bipin Anghan
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.upstox.ohlc.fsm" })
public class ApplicationConfig {
	@Bean
	public ServletRegistrationBean socketServlet() {
		return new ServletRegistrationBean(new StockServlet(), "/ohlc/update");
	}

	@Bean
	public SpringBeanUtil randomNameBeanUtil() {
		return new SpringBeanUtil();
	}

	@Bean
	public ClientSessionManager clientSessionMgr() {
		return new ClientSessionManager();
	}

}
