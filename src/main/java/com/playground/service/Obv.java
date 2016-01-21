package com.playground.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.utility.MapUtil;
import com.playground.utility.MyTickerComparator;

public class Obv {

	private static Logger log = Logger.getLogger(Obv.class);
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> indicatorMap1;
	
	public Map<String,ArrayList<Indicator>> doObv() {
		log.debug("----doObv----");
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());// sorts records in ascending order of dates
			for(int i=0;i<indicatorList.size();i++) {
				Indicator indicator = indicatorList.get(i);
				if(i ==0) {
					indicator.setObv(indicator.getTottrdqty());
				} else if(indicator.getClose() == indicatorList.get(i-1).getClose()) {
					indicator.setObv(indicatorList.get(i-1).getObv());
				} else if(indicator.getClose() < indicatorList.get(i-1).getClose()) {
					indicator.setObv(indicatorList.get(i-1).getObv() - indicator.getTottrdqty());
				} else {
					indicator.setObv(indicatorList.get(i-1).getObv() + indicator.getTottrdqty());
				}
				MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap1);
			}
		}
		log.debug("++++doObv++++");
		return indicatorMap1;
	}
	
	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}
	
	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}
	
	public Obv(Map<String, ArrayList<Indicator>> indicatorMap) {
		super();
		this.indicatorMap = indicatorMap;
	}
	
	public Obv() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap1 = new HashMap<String,ArrayList<Indicator>>();
	}
	
}
