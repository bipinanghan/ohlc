package com.ohlc;

import org.junit.Assert;
import org.junit.Test;

import com.upstox.ohlc.fsm.impl.FileStockProvider;
import com.upstox.ohlc.fsm.impl.FineStateMachineConsumer;
import com.upstox.ohlc.model.Stock;
import com.upstox.ohlc.model.StockOHLCDetail;
import com.upstox.ohlc.model.StockOHLCDetail.StockOHLCNotification;
import com.upstox.ohlc.utils.Utils;

/**
 * Junit test case of FineStateMachine
 * @author Bipin Anghan
 *
 */
public class FineStateMachineTest {

	@Test
	public void addStocktest() {
		FileStockProvider fileStockProvide = new FileStockProvider();
		FineStateMachineConsumer fineStateMachineConsumer = new FineStateMachineConsumer();
		Stock stock = Utils.toJsonObject(
				"{\"sym\":\"XZECXXBT\", \"T\":\"Trade\",  \"P\":0.01947, \"Q\":0.1, \"TS\":1538409720.3813, \"side\": \"s\", \"TS2\":1538409725339216503}",
				Stock.class);
		fileStockProvide.register(fineStateMachineConsumer);
		fineStateMachineConsumer.addStock(stock);
		StockOHLCDetail stockOHLCDetail = fineStateMachineConsumer.getOHLCDetailsBySymbol(stock.getSym());
		Assert.assertTrue(stockOHLCDetail.notifyStockOHLC().getH() == stock.getP());
	}

	@Test
	public void getOHLCDetailtest() {
		FileStockProvider fileStockProvide = new FileStockProvider();
		FineStateMachineConsumer fineStateMachineConsumer = new FineStateMachineConsumer();
		Stock stock1 = Utils.toJsonObject(
				"{\"sym\":\"XZECXXBT\", \"T\":\"Trade\",  \"P\":1, \"Q\":0.1, \"TS\":1538409720.3813, \"side\": \"s\", \"TS2\":1538409725339216503}",
				Stock.class);
		Stock stock2 = Utils.toJsonObject(
				"{\"sym\":\"XZECXXBT\", \"T\":\"Trade\",  \"P\":3, \"Q\":0.149159, \"TS\":1538411082.5662, \"side\": \"s\", \"TS2\":1538409725339216700}",
				Stock.class);
		Stock stock3 = Utils.toJsonObject(
				"{\"sym\":\"XZECXXBT\", \"T\":\"Trade\",  \"P\":2, \"Q\":2.45055, \"TS\":1538411774.9835, \"side\": \"s\", \"TS2\":1538409725339216800}",
				Stock.class);
		fileStockProvide.register(fineStateMachineConsumer);
		fineStateMachineConsumer.addStock(stock1);
		fineStateMachineConsumer.addStock(stock2);
		fineStateMachineConsumer.addStock(stock3);
		StockOHLCDetail stockOHLCDetail = fineStateMachineConsumer.getOHLCDetailsBySymbol(stock1.getSym());
		StockOHLCNotification stockOHLCNotification = stockOHLCDetail.notifyStockOHLC();
		Assert.assertTrue(stockOHLCNotification.getO() == stock1.getP());
		Assert.assertTrue(stockOHLCNotification.getH() == stock2.getP());
		Assert.assertTrue(stockOHLCNotification.getL() == stock1.getP());
		Assert.assertTrue(stockOHLCNotification.getC() == stock3.getP());
	}
}
