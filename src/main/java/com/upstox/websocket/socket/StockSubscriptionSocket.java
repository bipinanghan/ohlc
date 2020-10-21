package com.upstox.websocket.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upstox.ohlc.model.Event;
import com.upstox.ohlc.utils.SpringBeanUtil;
import com.upstox.ohlc.utils.Utils;
import com.upstox.websocket.service.ClientSessionManager;

/**
 * This class is used to establish socket connection. 
 * @author Bipin Anghan
 *
 */
@WebSocket
public class StockSubscriptionSocket {

	private static final Logger		logger			= LoggerFactory.getLogger(StockSubscriptionSocket.class);

	private ClientSessionManager	clientSessionManager;
	private static long				wsConnections	= 0;

	@OnWebSocketConnect
	public void onConnect(Session session) {
		wsConnections++;
		logger.info("Total connections so far active {}", wsConnections);
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) {
		if (null == clientSessionManager)
		{
			clientSessionManager = SpringBeanUtil.getBean(ClientSessionManager.class);
		}
		Event event = Utils.toJsonObject(message, Event.class);
		clientSessionManager.putSession(event.getSymbol(), session);
	}

	@OnWebSocketClose
	public void onClose(Session session, int _closeCode, String _closeReason) {
		wsConnections--;
		logger.info("Websocket connection destroyed, so far count is {}", wsConnections);
	}
}
