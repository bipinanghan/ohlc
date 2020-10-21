package com.upstox.ohlc.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upstox.ohlc.fsm.StockNotifier;

/**
 * Model to represent stock ohlc detail. 
 * @author Bipin Anghan
 *
 */
public class StockOHLCDetail {
	private static final Logger	logger					= LoggerFactory.getLogger(StockOHLCDetail.class);

	private static final int	DEFAULT_INTERVAL_IN_SEC	= 15;

	// Stock OHLC numeric position.
	private static final int	OPEN					= 0;
	private static final int	HIGH					= 1;
	private static final int	LOW						= 2;
	private static final int	CLOSE					= 3;

	private String				symbol;
	private volatile int		barNumber;
	private int					intervalInSec;
	private long				nextStockPublishTime;
	private volatile double		lastPrice;
	private volatile boolean	isReset;

	private volatile double[]	stockOHLCc				= new double[4];

	private StockNotifier		updater;

	public StockOHLCDetail(Stock st, StockNotifier p) {
		this(st, p, DEFAULT_INTERVAL_IN_SEC);
	}

	public StockOHLCDetail(Stock st, StockNotifier p, int intervalInSec) {
		this.symbol = st.getSym();
		this.barNumber = 1;
		this.intervalInSec = intervalInSec;
		setOHLC(st);
		lastPrice = st.getP();
		updater = p;
	}

	private void setOHLC(Stock st) {
		this.nextStockPublishTime = (st.getTS2() / 1000000L) + (this.intervalInSec * 1000);
		stockOHLCc[OPEN] = st.getP();
		stockOHLCc[HIGH] = st.getP();
		stockOHLCc[LOW] = st.getP();
		stockOHLCc[CLOSE] = 0.00;
	}

	public void recompute(Stock st) {
		logger.debug("recomputing Stock for {}", st.getSym());
		double latestPrice = st.getP();
		if (isReset)
		{
			setOHLC(st);
			isReset = false;
		}

		if (latestPrice > stockOHLCc[HIGH])
		{
			stockOHLCc[HIGH] = latestPrice;
		}
		else if (latestPrice < stockOHLCc[LOW])
		{
			stockOHLCc[LOW] = latestPrice;
		}
		lastPrice = latestPrice;
		if ((st.getTS2() / 1000000L) >= nextStockPublishTime)
		{
			logger.debug("OHLC detail publishing is {}", st);
			updater.publish(notifyStockOHLC());
		}
	}

	public StockOHLCNotification notifyStockOHLC() {
		StockOHLCNotification ret = new StockOHLCNotification(this);
		reset();
		return ret;
	}

	private void reset() {
		stockOHLCc[OPEN] = 0.00;
		stockOHLCc[HIGH] = 0.00;
		stockOHLCc[LOW] = 0.00;
		stockOHLCc[CLOSE] = 0.00;
		lastPrice = 0.00;
		barNumber += 1;
		isReset = true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockOHLCDetail other = (StockOHLCDetail) obj;
		if (symbol == null)
		{
			if (other.symbol != null)
				return false;
		}
		else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public static class StockOHLCNotification {
		private String	symbol;
		private int		barNumber;
		private double	o;
		private double	h;
		private double	l;
		private double	c;
		@JsonIgnore
		private int		interval;

		public StockOHLCNotification(StockOHLCDetail stockBar) {
			this.symbol = stockBar.symbol;
			this.barNumber = stockBar.barNumber;
			this.o = stockBar.stockOHLCc[OPEN];
			this.h = stockBar.stockOHLCc[HIGH];
			this.l = stockBar.stockOHLCc[LOW];
			this.c = stockBar.lastPrice;
			this.interval = stockBar.intervalInSec;
		}

		public String getSymbol() {
			return symbol;
		}

		public int getBarNumber() {
			return barNumber;
		}

		public double getO() {
			return o;
		}

		public double getH() {
			return h;
		}

		public double getL() {
			return l;
		}

		public double getC() {
			return c;
		}

		public int getInterval() {
			return interval;
		}

		@Override
		public String toString() {
			return o + " " + h + " " + l + " " + c;
		}

	}
}
