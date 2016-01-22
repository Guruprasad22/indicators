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
import com.playground.utility.MapUtil;
import com.playground.utility.MyTickerComparator;

public class ForceIndex {

	private static Logger log = Logger.getLogger(ForceIndex.class);
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> indicatorMap1;
	private Map<String,ArrayList<Indicator>> indicatorMap2;
	
	public Map<String,ArrayList<Indicator>> doForceIndex() {
		
		log.debug("----doForceIndex----");
		//calculate the force index for each ticker
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());// sorts records in ascending order of dates
			BigDecimal fi = new BigDecimal("0.0").setScale(1, BigDecimal.ROUND_HALF_UP);
			for(int i=1;i<indicatorList.size();i++) {
				if(i == 1) {
					MapUtil.updateIndicatorMap(entry.getKey(),indicatorList.get(i-1),indicatorMap1);
				}
				Indicator indicator= indicatorList.get(i);
				fi = new BigDecimal(indicator.getTottrdqty() * (indicator.getClose() - indicatorList.get(i-1).getClose()));
				indicator.setFi(fi);
				MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap1);
			}
		}
		//calculate the 13 day ema of force index
		entries = indicatorMap1.entrySet().iterator();
		
		while(entries.hasNext()) { // for each ticker
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator()); // sorts records in ascending order of dates
			
			//set the first 12 fi to ZERO
			if(indicatorList.size() >= 12) {
				for(int i=0; i < 12; i++) {
					Indicator indicator = indicatorList.get(i);
					indicator.setFi13(new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP));
					MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap2);
				}
				
				// calculate the 13 day ema
				float k = (float) 2/14;
				BigDecimal fi13 = new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP);
				for(int i = 12; i < indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if( i == 12)
						fi13 = (indicatorList.get(i-1).getFi().multiply(new BigDecimal((1-k)))).add(indicator.getFi().multiply(new BigDecimal(k)));
					else
						fi13 = (indicatorList.get(i-1).getFi13().multiply(new BigDecimal((1-k)))).add(indicator.getFi().multiply(new BigDecimal(k)));
					indicator.setFi13(fi13);
					MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap2);
				}				
			}
		}// end for each ticker
		log.debug("++++doForceIndex++++");
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

	public ForceIndex() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap1 = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap2 = new HashMap<String,ArrayList<Indicator>>();
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap2() {
		return indicatorMap2;
	}

	public void setIndicatorMap2(Map<String, ArrayList<Indicator>> indicatorMap2) {
		this.indicatorMap2 = indicatorMap2;
	}
}
