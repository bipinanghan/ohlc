package com.upstox.websocket.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.upstox.websocket.socket.StockSubscriptionSocket;

/**
 * Class is used to to create websocket based on servlet to provide stock update
 * @author Bipin Anghan
 *
 */
@SuppressWarnings("serial")
public class StockServlet extends WebSocketServlet {

	@Override
	public void configure(WebSocketServletFactory webSocketServletFactory) {
		webSocketServletFactory.register(StockSubscriptionSocket.class);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

}
