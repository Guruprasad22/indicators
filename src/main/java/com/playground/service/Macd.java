package com.playground.service;

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
	private Map<String,ArrayList<Indicator>> finalIndicatorMap;
	private static Logger log = Logger.getLogger(Macd.class);
	
	
	public Map<String,ArrayList<Indicator>> doMacd() {
		
		log.debug("+++++ doMacd +++++");
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			
			//set the first 25 to ZERO
			for(int i =0; i<25; i++) {
				Indicator indicator = indicatorList.get(i);
				indicator.setFastMacd(0);
				updateIndicatorMap(entry.getKey(),indicator);
			}
			
			if(indicatorList.size() >= 26) {
				for(int i =25; i < indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					float fastMacd = indicator.getEma12() - indicator.getEma26();
					indicator.setFastMacd(fastMacd);
					updateIndicatorMap(entry.getKey(),indicator);
				}
			}
		}
		log.debug("+++++ doMacd +++++");
		return finalIndicatorMap;
	}
	
	public void updateIndicatorMap(String key,Indicator i) {
		if(finalIndicatorMap.containsKey(key)) {
			finalIndicatorMap.get(key).add(i);
		}else {
			ArrayList<Indicator> tList = new ArrayList<Indicator>();
			tList.add(i);
			finalIndicatorMap.put(key,tList);
		}
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getFinalIndicatorMap() {
		return finalIndicatorMap;
	}

	public void setFinalIndicatorMap(Map<String, ArrayList<Indicator>> finalIndicatorMap) {
		this.finalIndicatorMap = finalIndicatorMap;
	}

	public Macd(Map<String, ArrayList<Indicator>> indicatorMap, Map<String, ArrayList<Indicator>> finalIndicatorMap) {
		super();
		this.indicatorMap = indicatorMap;
		this.finalIndicatorMap = finalIndicatorMap;
	}

	public Macd() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		finalIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
	}
}
		