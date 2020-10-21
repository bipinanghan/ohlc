package com.upstox.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application Startup class
 * @author Bipin Anghan
 *
 */
@ComponentScan("com.upstox.websocket")
@EnableAutoConfiguration
public class UpstoxApplicationStartup {

	public static void main(String[] args) {
		SpringApplication.run(UpstoxApplicationStartup.class, args);
	}
}
