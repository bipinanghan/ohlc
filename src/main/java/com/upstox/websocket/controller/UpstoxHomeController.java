package com.upstox.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upstox.websocket.service.ClientSessionManager;

@Controller
@RequestMapping("/upstox/*")
public class UpstoxHomeController {

	@Autowired
	ClientSessionManager sessionManager;

	@GetMapping("/ohlc")
	public String ohlc() {
		return "/stockohlc.html";
	}
}
