package com.playground.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.utility.MyTickerComparator;

public class Ema12 {

	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> finalIndicatorMap;
	
	private static Logger log = Logger.getLogger(Ema12.class);
	
	public Map<String,ArrayList<Indicator>> doEma() {
		
		log.debug("+++++ doEma +++++");
		
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			
			//set the first 11 emas to ZERO for ema12
			if(indicatorList.size() >= 12) {
				for(int i=0; i < 12; i++) {
					Indicator indicator = indicatorList.get(i);
					indicator.setEma12(new BigDecimal("0.000"));
					updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
				}
				
				// calculate the 12 day ema
				float k = (float) 2/13;
				BigDecimal ema12;
				for(int i = 12; i < indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if( i == 12)
						ema12 = indicator.getSma().multiply(new BigDecimal(1-k)).add( new BigDecimal(indicator.getClose()).multiply(new BigDecimal(k))); 
					else
						ema12 = (indicatorList.get(i-1).getEma12().multiply(new BigDecimal(1-k))).add( new BigDecimal(indicator.getClose()).multiply(new BigDecimal(k)));
					ema12 = ema12.setScale(3, RoundingMode.HALF_UP);
					indicator.setEma12(ema12);
					updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
				}				
			}
		}// end for each ticker
		log.debug("----- doEma -----");
		return finalIndicatorMap;

	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public Ema12() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		finalIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
		// TODO Auto-generated constructor stub
	}

	public Ema12(Map<String, ArrayList<Indicator>> indicatorMap, Map<String, ArrayList<Indicator>> finalindicatorMap) {
		super();
		this.indicatorMap = indicatorMap;
		this.finalIndicatorMap = finalindicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getFinalindicatorMap() {
		return finalIndicatorMap;
	}

	public void setFinalindicatorMap(Map<String, ArrayList<Indicator>> finalindicatorMap) {
		this.finalIndicatorMap = finalindicatorMap;
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
}
