package com.upstox.ohlc.fsm;

import com.upstox.ohlc.model.Stock;

/**
 * Stock Provide 
 * @author Bipin Anghan
 *
 */
public interface StockProvider {

	/**
	 * Easy way to allow {@link StockConsumer} to register for Stock changes.
	 * @param c
	 * @return true if subscribe is successful.
	 */
	public boolean register(StockConsumer c);

	/**
	 * Easy way to allow {@link StockConsumer} to de-register of Stock changes.
	 * @param c
	 * @return true if subscribe is successful.
	 */
	public boolean deregister(StockConsumer c);

	/**
	 * Share the stock Stock changes with all {@link StockConsumer}
	 * @param t
	 */
	void notify(Stock t);
}
