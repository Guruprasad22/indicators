package com.playground.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.utility.MyTickerComparator;

public class Ema {

	private Map<String,ArrayList<Indicator>> indicatorMap;// = new HashMap<String,ArrayList<Indicator>>();
	private Map<String,ArrayList<Indicator>> finalIndicatorMap;// = new HashMap<String,ArrayList<Indicator>>();
	
	private static Logger log = Logger.getLogger(Ema.class);
	
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
					Indicator indicator = new Indicator(indicatorList.get(i));
					indicator.setEma12(0);
					updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
				}
				
				// calculate the 12 day ema
				float k = (float) 2/13;
				float ema12;
				for(int i = 12; i < indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if( i == 12)
						ema12 = (indicator.getSma() * (1-k)) + ( indicator.getClose() * k);
					else
						ema12 = (indicatorList.get(i-1).getEma12() * (1-k)) + ( indicator.getClose() * k);
					indicator.setEma12(ema12);
					updateIndicatorMap(indicator.getSymbol()+ "+" + indicator.getSeries(),indicator);
				}				
			}
			
/*			if(indicatorList.size() >= 26) {
				//set the first 25 emas to ZERO for ema26
				for(int i=0; i < 26; i++) {
					Indicator indicator = new Indicator(indicatorList.get(i));
					indicator.setEma26(0);
				}

				// calculate the 26 day ema
				for(int i = 26; i < indicatorList.size(); i++) {
					Indicator indicator = new Indicator(indicatorList.get(i));
					float k = 2/27;
					float ema26 = 0;
					if( i == 26)
						ema26 = (indicator.getClose() * k) + (indicator.getSma() * (1-k)); // need sma for 26 days 
					else 
						ema26 = (indicator.getClose() * k) + (indicatorList.get(i-1).getEma26() *(1-k));
					indicator.setEma26(ema26);
				}				
			}*/
			
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

	public Ema() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		finalIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
		// TODO Auto-generated constructor stub
	}

	public Ema(Map<String, ArrayList<Indicator>> indicatorMap, Map<String, ArrayList<Indicator>> finalindicatorMap) {
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

