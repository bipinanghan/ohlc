package com.upstox.ohlc.fsm.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.upstox.ohlc.fsm.StockConsumer;
import com.upstox.ohlc.fsm.StockProvider;
import com.upstox.ohlc.model.Stock;
import com.upstox.ohlc.utils.Utils;

/**
 * This class is used to read stock details and publish from file
 * @author Bipin Anghan
 *
 */
@Component
public class FileStockProvider implements StockProvider {

	private static final String	TRADE_FILE_PATH	= "trade.file.path";

	@Autowired
	private Set<StockConsumer>	stockConsumers	= new HashSet<>();

	@Autowired
	private Environment			environement;

	private static final Logger	logger			= LoggerFactory.getLogger(FileStockProvider.class);

	@Override
	public boolean register(StockConsumer stockConsumer) {
		return stockConsumers.add(stockConsumer);
	}

	@Override
	public boolean deregister(StockConsumer stockConsumer) {
		return stockConsumers.remove(stockConsumer);
	}

	@Override
	public void notify(Stock t) {
		stockConsumers.parallelStream().forEach(stockConsumer -> stockConsumer.update(t));
	}

	@PostConstruct
	public void execute() {
		new Thread(() -> {
			String absFilePath = environement.getProperty(TRADE_FILE_PATH);
			logger.info("Absolute trade file path set using -Dtrade.file.path program args is {}.", absFilePath);
			try (BufferedReader br = new BufferedReader(new FileReader(absFilePath)))
			{
				String data;
				while ((data = br.readLine()) != null)
				{
					this.notify(Utils.toJsonObject(data, Stock.class));
					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						logger.error("File reading at 10ms interval interrupted  " + e.getMessage());
					}
				}
			}
			catch (IOException e)
			{
				logger.error("File trades.json not found. exception is " + e.getMessage());
			}
		}).start();
	}

}
