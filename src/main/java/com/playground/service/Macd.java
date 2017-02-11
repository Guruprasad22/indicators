package com.playground.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.utility.MyTickerComparator;

public class Macd {
	
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> fastMacdIndicatorMap;
	private Map<String,ArrayList<Indicator>> slowMacdIndicatorMap;
	private Map<String,ArrayList<Indicator>> histogramIndicatorMap;
	private static Logger log = Logger.getLogger(Macd.class);
	
	
	public Map<String,ArrayList<Indicator>> doMacd() {
		
		log.debug("+++++ doMacd +++++");
		log.debug("------doFastMACD------");
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			
			//set the first 25 to ZERO
			for(int i =0; i<25; i++) {
				Indicator indicator = indicatorList.get(i);
				indicator.setFastMacd(new BigDecimal("0.000"));
				updateIndicatorMap(entry.getKey(),indicator,fastMacdIndicatorMap);
			}
			
			if(indicatorList.size() >= 26) {
				for(int i =25; i < indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					BigDecimal fastMacd = indicator.getEma12().subtract(indicator.getEma26());
					indicator.setFastMacd(fastMacd);
					updateIndicatorMap(entry.getKey(),indicator,fastMacdIndicatorMap);
				}
			}
		}
		
		log.debug("+++++++doFastMacd++++++++");
		log.debug("---------doslowMacd-------");
		entries = fastMacdIndicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
//			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			if(indicatorList.size() >= 36) {
				//set the first 35 to ZERO
				for(int i =0; i<35; i++) {
					Indicator indicator = indicatorList.get(i);
					indicator.setSlowMacd(new BigDecimal("0.000"));
					updateIndicatorMap(entry.getKey(),indicator,slowMacdIndicatorMap);
				}
				
				float k = (float) 2/10;
				BigDecimal slowEma;
				for(int i=35; i<indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if( i == 35)
						slowEma = indicator.getFastMacd();
					else
						slowEma = (indicatorList.get(i-1).getFastMacd().multiply(new BigDecimal(1-k))).add(indicator.getFastMacd().multiply(new BigDecimal(k)));
					indicator.setSlowMacd(slowEma);
					updateIndicatorMap(entry.getKey(),indicator,slowMacdIndicatorMap);
				}
			}
		}
		fastMacdIndicatorMap.clear();
		log.debug("+++++doslowMacd+++++");
		
		entries = slowMacdIndicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			if(indicatorList.size() >= 36) {
				for(int i=0; i<=34; i++) {
					Indicator indicator = indicatorList.get(i);
					updateIndicatorMap(entry.getKey(),indicator,histogramIndicatorMap);
				}
				for(int i=35; i<indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					indicator.setHistogram((indicator.getFastMacd().subtract(indicator.getSlowMacd())).multiply(new BigDecimal("100")));
					updateIndicatorMap(entry.getKey(),indicator,histogramIndicatorMap);
				}
			} else {
				for(int i=0; i<indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					updateIndicatorMap(entry.getKey(),indicator,histogramIndicatorMap);
				}
			}
		}
		log.debug("+++++ doMacd +++++");
		return histogramIndicatorMap;
	}
	
	public Map<String,ArrayList<Indicator>> updateIndicatorMap(String key,Indicator i,Map<String,ArrayList<Indicator>> myMap) {
		if(myMap.containsKey(key)) {
			myMap.get(key).add(i);
		}else {
			ArrayList<Indicator> tList = new ArrayList<Indicator>();
			tList.add(i);
			myMap.put(key,tList);
		}
		return myMap;
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getFinalIndicatorMap() {
		return fastMacdIndicatorMap;
	}

	public void setFinalIndicatorMap(Map<String, ArrayList<Indicator>> finalIndicatorMap) {
		this.fastMacdIndicatorMap = finalIndicatorMap;
	}

	public Macd(Map<String, ArrayList<Indicator>> indicatorMap, Map<String, ArrayList<Indicator>> finalIndicatorMap) {
		super();
		this.indicatorMap = indicatorMap;
		this.fastMacdIndicatorMap = finalIndicatorMap;
	}

	public Macd() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		fastMacdIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
		slowMacdIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
		histogramIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
	}

	public Map<String, ArrayList<Indicator>> getSlowMacdIndicatorMap() {
		return slowMacdIndicatorMap;
	}

	public void setSlowMacdIndicatorMap(Map<String, ArrayList<Indicator>> slowMacdIndicatorMap) {
		this.slowMacdIndicatorMap = slowMacdIndicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getHistogramIndicatorMap() {
		return histogramIndicatorMap;
	}

	public void setHistogramIndicatorMap(Map<String, ArrayList<Indicator>> histogramIndicatorMap) {
		this.histogramIndicatorMap = histogramIndicatorMap;
	}
}
