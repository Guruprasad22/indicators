package com.playground.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.utility.MyTickerComparator;

public class SimpleMovingAverage {
	
	private static Logger log = Logger.getLogger(SimpleMovingAverage.class);
	Map<String,ArrayList<Ticker>> tickerMap;// = new HashMap<String,ArrayList<Ticker>>();
	Map<String,ArrayList<Indicator>> indicatorMap;// = new HashMap<String,ArrayList<Indicator>>();
	
	
	// break the entire list into group of stock+series and populate to a map
	public void compileMapOfIndividualStocks() throws SQLException, IOException {
		log.info("-----------compileMapOfIndividualStocks-------------");
		List<Ticker> tickerList = new DatabaseService().getAllEquityTickers();
		
		ArrayList<Ticker> stockList = new ArrayList<Ticker>();
		for(Ticker ticker : tickerList) {
			String key = ticker.getSymbol()+ "+" + ticker.getSeries();
			if(tickerMap.containsKey(key)) {
				tickerMap.get(key).add(ticker);
			}else {
				ArrayList<Ticker> tList = new ArrayList<Ticker>();
				tList.add(ticker);
				tickerMap.put(key,tList);
			}
		}
		log.info("+++++++++++compileMapOfIndividualStocks+++++++++++++");
	}
	
	/**
	 * chances of stock not being traded on all trading days will not be handled
	 * interval will be used to calculate moving average
	 * 
	 * @param interval
	 * @throws ParseException 
	 */
	public Map<String, ArrayList<Indicator>> calculateSma(int interval) throws ParseException {
		
		Iterator<Entry<String, ArrayList<Ticker>>> entries = tickerMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			
			Map.Entry<String, ArrayList<Ticker>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Ticker> tickerList = entry.getValue();
			Collections.sort(tickerList,new MyTickerComparator()); // sorts records in ascending order of dates
			ArrayList<BigDecimal> closingPrice = new ArrayList<BigDecimal>();
			
			//set the indicators before interval as having 0 sma
			int iterations = 0; 
			
			if(tickerList.size() >= interval ) {
				iterations = interval-1;
			} else {
				iterations = tickerList.size()-1;
			}
				
			for(int i=0;i<iterations; i++) {
//				log.debug("i is : " + i + " iterations is " + iterations);
				Indicator indicator = new Indicator(tickerList.get(i));
				indicator.setSma(new BigDecimal("0.000"));
//				log.info(indicator);
				updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
			}
			
			int count = 0;
			
			for(int i = interval-1; i< tickerList.size(); i++) {
				Indicator indicator = new Indicator(tickerList.get(i));
				for(int j=count++; j<=i;j++) {
					closingPrice.add(new BigDecimal(tickerList.get(j).getClose()));
				}
				if(closingPrice.isEmpty()) {
					log.debug("sma list is empty how come??");
//					indicator.setSma(0);
				} else {
					BigDecimal ma = new BigDecimal("0.000");
					for(int k=0; k<closingPrice.size(); k++) {
						ma = ma.add(closingPrice.get(k));
					}
					if(closingPrice.size() < interval-1) {
						indicator.setSma(new BigDecimal("0.000"));
					} else {
						BigDecimal divider = new BigDecimal(closingPrice.size());
						indicator.setSma(ma.divide(divider,3,RoundingMode.HALF_UP));
					}
				}
				updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
				closingPrice.clear();
			}

 		} // end for each ticker

		return indicatorMap;
	}
	
	public Map<String, ArrayList<Ticker>> getTickerMap() {
		return tickerMap;
	}

	public void setTickerMap(Map<String, ArrayList<Ticker>> tickerMap) {
		this.tickerMap = tickerMap;
	}

	public SimpleMovingAverage(Map<String, ArrayList<Ticker>> tickerMap,
			Map<String, ArrayList<Indicator>> indicatorMap) {
		super();
		this.tickerMap = tickerMap;
		this.indicatorMap = indicatorMap;
	}

	public SimpleMovingAverage() {
		super();
		tickerMap = new HashMap<String,ArrayList<Ticker>>();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		// TODO Auto-generated constructor stub
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public void updateIndicatorMap(String key,Indicator i) {
		if(indicatorMap.containsKey(key)) {
			indicatorMap.get(key).add(i);
		}else {
			ArrayList<Indicator> tList = new ArrayList<Indicator>();
			tList.add(i);
			indicatorMap.put(key,tList);
		}
	}
}
