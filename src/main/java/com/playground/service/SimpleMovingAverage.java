package com.playground.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.utility.MyDateComparator;
import com.playground.utility.MyDateDiff;

public class SimpleMovingAverage {
	
	private static Logger log = Logger.getLogger(SimpleMovingAverage.class);
	Map<String,ArrayList<Ticker>> tickerMap = new HashMap<String,ArrayList<Ticker>>();
	
	// break the entire list into group of stock+series and populate to a map
	public void compileMapOfIndividualStocks() throws SQLException, IOException {
		log.info("-----------compileMapOfIndividualStocks-------------");
		List<Ticker> tickerList = new DatabaseService().getAllTickers();
		
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
	public List<Indicator> calculateSma(int interval) throws ParseException {
		List<Indicator> indicatorList = new ArrayList<Indicator>();
		Iterator<Entry<String, ArrayList<Ticker>>> entries = tickerMap.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, ArrayList<Ticker>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Ticker> tickerList = entry.getValue();
			Collections.sort(tickerList,new MyDateComparator());
			ArrayList<Float> sma = new ArrayList<Float>();
			//set the indicators before interval as having 0 sma
			for(int i=0; i<interval-1; i++) {
				Indicator indicator = new Indicator(tickerList.get(i));
				indicator.setSma(0);
				indicatorList.add(indicator);
			}
			int count = 0;
			for(int i = interval-1; i< tickerList.size(); i++) {
				Indicator indicator = new Indicator(tickerList.get(i));
				for(int j=count++; j<i;j++) {
						sma.add(tickerList.get(j).getClose());
					}
				if(sma.isEmpty()) {
					log.debug("sma list is empty how come??");
//					indicator.setSma(0);
				} else {
					float ma = 0;
					for(int k=0; k<sma.size(); k++) {
						ma = ma + sma.get(k);
					}
					if(sma.size() < interval-1) {
						indicator.setSma(0);
					} else {
						indicator.setSma(ma/sma.size());
					}
				}
				indicatorList.add(indicator);
				sma.clear();
			}
 		}
		for(int l=0; l<indicatorList.size(); l++) {
			log.info(indicatorList.get(l));
		}
		return indicatorList;
	}

	public Map<String, ArrayList<Ticker>> getTickerMap() {
		return tickerMap;
	}

	public void setTickerMap(Map<String, ArrayList<Ticker>> tickerMap) {
		this.tickerMap = tickerMap;
	}

	public SimpleMovingAverage(Map<String, ArrayList<Ticker>> tickerMap) {
		super();
		this.tickerMap = tickerMap;
	}

	public SimpleMovingAverage() {
		super();
	}
}
