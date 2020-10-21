package com.upstox.ohlc.fsm;

import com.upstox.ohlc.model.Stock;

/**
 * Stock Consumer 
 * @author Bipin Anghan
 *
 */
public interface StockConsumer {

	/**
	 * update stock to client
	 * @param data
	 */
	public void update(Stock data);
}
