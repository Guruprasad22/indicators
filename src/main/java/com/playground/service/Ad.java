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
import com.playground.utility.MapUtil;
import com.playground.utility.MyTickerComparator;

public class Ad {
	
	private static Logger log = Logger.getLogger(Ad.class);
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> indicatorMap1;
	
	public Map<String,ArrayList<Indicator>> doAccumulationDistribution() {
		log.debug("----doAccumulationDistribution----");
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());// sorts records in ascending order of dates
			BigDecimal ad = new BigDecimal("0.000");
			for(int i=0;i<indicatorList.size();i++) {
				Indicator indicator = indicatorList.get(i);
				ad = new BigDecimal(((indicator.getClose() - indicator.getOpen()) / (indicator.getHigh() - indicator.getLow())) * indicator.getTottrdqty()).setScale(1, BigDecimal.ROUND_HALF_UP);
				indicator.setAd(ad);
				MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap1);
			}
		}
		log.debug("++++doAccumulationDistribution++++");
		return indicatorMap1;
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap1() {
		return indicatorMap1;
	}

	public void setIndicatorMap1(Map<String, ArrayList<Indicator>> indicatorMap1) {
		this.indicatorMap1 = indicatorMap1;
	}

	public Ad() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap1 = new HashMap<String,ArrayList<Indicator>>();
	}
	
	
	
	
	
}
