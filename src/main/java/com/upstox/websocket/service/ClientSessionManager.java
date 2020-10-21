package com.upstox.websocket.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.springframework.util.Assert;

/**
 * It manage all client session.
 * @author Bipin Anghan
 *
 */
public class ClientSessionManager {

	private Map<String, Set<Session>> symbolSessionMap = new ConcurrentHashMap<>();

	@SuppressWarnings("serial")
	public void putSession(String symbol, Session session) {
		Assert.notNull(symbol, "Symbol cannot be null.");
		Assert.notNull(session, "User session cannot be null.");
		Set<Session> socketConnectionSet = symbolSessionMap.get(symbol);
		if (org.springframework.util.CollectionUtils.isEmpty(socketConnectionSet))
		{
			symbolSessionMap.put(symbol, new HashSet<Session>() {
				{
					add(session);
				}
			});
		}
		else
		{
			symbolSessionMap.get(symbol).add(session);
		}
	}

	public Set<Session> getSession(String symbol) {
		return symbolSessionMap.get(symbol);
	}
}
