package com.upstox.ohlc.fsm;

import com.upstox.ohlc.model.StockOHLCDetail.StockOHLCNotification;

/**
 * Stock Notifier 
 * @author Bipin Anghan
 *
 */
public interface StockNotifier {

	/**
	 * Method for stock to publish its OHLC data.
	 * @param stock
	 */
	public void publish(StockOHLCNotification stockOhlcNotification);
}
