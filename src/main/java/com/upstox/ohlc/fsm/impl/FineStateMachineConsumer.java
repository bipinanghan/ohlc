package com.upstox.ohlc.fsm.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.upstox.ohlc.fsm.StockConsumer;
import com.upstox.ohlc.fsm.StockNotifier;
import com.upstox.ohlc.model.Stock;
import com.upstox.ohlc.model.StockOHLCDetail;
import com.upstox.ohlc.model.StockOHLCDetail.StockOHLCNotification;
import com.upstox.ohlc.utils.Utils;
import com.upstox.websocket.service.ClientSessionManager;

/**
 * This class is behave like Finite State Machine which consumes stock update and notify to client. 
 * @author Bipin Anghan
 *
 */
@Component
public class FineStateMachineConsumer implements StockConsumer, StockNotifier {

	private static final Logger				logger			= LoggerFactory.getLogger(FineStateMachineConsumer.class);

	private Map<String, StockOHLCDetail>	ohlcMap			= new HashMap<>();
	ExecutorService							executorService	= Executors.newFixedThreadPool(1);

	@Autowired
	private ClientSessionManager			clientSessionMgr;

	public StockOHLCDetail getOHLCDetailsBySymbol(String symbol) {
		StockOHLCDetail stockOhlcDetail = ohlcMap.get(symbol);
		return stockOhlcDetail;
	}

	public void addStock(Stock stock) {
		if (null != stock)
		{
			StockOHLCDetail current = ohlcMap.get(stock.getSym());
			if (null != current)
			{
				current.recompute(stock);
			}
			else
			{
				ohlcMap.put(stock.getSym(), new StockOHLCDetail(stock, this));
			}
		}
	}

	@Override
	public void update(Stock stock) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				addStock(stock);
			}
		});
	}

	@Override
	public void publish(StockOHLCNotification stock) {
		String msg = Utils.toJsonString(stock);
		logger.debug("publishing : {}", msg);
		if (clientSessionMgr != null)
		{
			Set<Session> sessions = clientSessionMgr.getSession(stock.getSymbol());
			if (CollectionUtils.isEmpty(sessions))
			{
				logger.warn("Stock is publish for {}, but no active sessions.", stock.getSymbol());
				return;
			}
			logger.info(msg);
			sessions.parallelStream().filter(s -> s.isOpen()).forEach(s -> {
				try
				{
					s.getRemote().sendString(msg);
				}
				catch (IOException e)
				{
					logger.error("Sending stock over ws failed. Error", e);
				}
			});
		}
	}

	@PreDestroy
	public void stopWorkers() {
		try
		{
			logger.info("initiating shutdown for FSM thread pool.");
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		}
		catch (Exception e)
		{
			logger.error("Erro while FSM preDestroy call", e);
		}
	}

}
