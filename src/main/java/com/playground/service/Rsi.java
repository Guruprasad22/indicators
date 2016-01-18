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

public class Rsi {
	
	private static Logger log = Logger.getLogger(Rsi.class);
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> indicatorMap1;
	private Map<String,ArrayList<Indicator>> indicatorMap2;
	private Map<String,ArrayList<Indicator>> indicatorMap3;
	
	public Map<String,ArrayList<Indicator>> doRsi() {
		
		//first calculate the gain and loss
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			if(indicatorList.size() >= 14) {
				Collections.sort(indicatorList,new MyTickerComparator());
				for(int i=1; i<indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if(indicator.getClose() >= indicatorList.get(i-1).getClose()) {
						indicator.setGain(indicator.getClose() - indicatorList.get(i-1).getClose());
						indicator.setLoss(0);
					} else {
						indicator.setLoss(indicatorList.get(i-1).getClose() - indicator.getClose());
						indicator.setGain(0);
					}
					MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap1);
				}				
			}
		}
		
		//now average the gain and loss
		entries = indicatorMap1.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			if(indicatorList.size() >= 14) {
				Collections.sort(indicatorList,new MyTickerComparator());
				float avgGain = 0;
				float avgLoss = 0;
				for(int i=0; i<13; i++) {
					Indicator indicator = indicatorList.get(i);
					avgGain = avgGain + indicatorList.get(i).getGain();
					avgLoss = avgLoss + indicatorList.get(i).getLoss();
					MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
				}
				Indicator indicator = indicatorList.get(13);
				indicator.setAvgGain((float)avgGain/14);
				indicator.setAvgLoss((float)avgLoss/14);
				MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
				for(int i=14;i< indicatorList.size();i++) {
					indicator = indicatorList.get(i);
					avgGain = (float) ((indicatorList.get(i-1).getAvgGain() *13) + indicator.getGain())/14;
					avgLoss = (float) ((indicatorList.get(i-1).getLoss() * 13) + indicator.getLoss())/14;
					indicator.setAvgGain(avgGain);
					indicator.setAvgLoss(avgLoss);
					MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
				}				
			}

		}
		indicatorMap1.clear();
		//finally calculate RS and RSI
		entries = indicatorMap2.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			if(indicatorList.size() >= 14) {

				Collections.sort(indicatorList,new MyTickerComparator());
				for(int i=0;i<13;i++) {
					MapUtil.updateIndicatorMap(entry.getKey(),indicatorList.get(i),indicatorMap3);
				}
				float rsi = 0;
				for(int i=13;i<indicatorList.size();i++) {
					Indicator indicator = indicatorList.get(i);
					if(indicator.getAvgLoss() == 0) {
						indicator.setRsi(0);
					} else {
						rsi = 100 - (100/(1+(indicator.getAvgGain()/indicator.getAvgLoss())));
						indicator.setRsi(rsi);
					}
					MapUtil.updateIndicatorMap(entry.getKey(),indicatorList.get(i),indicatorMap3);
				}			
			}

		}
		indicatorMap2.clear();
		return indicatorMap3;
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

	public Map<String, ArrayList<Indicator>> getIndicatorMap2() {
		return indicatorMap2;
	}

	public void setIndicatorMap2(Map<String, ArrayList<Indicator>> indicatorMap2) {
		this.indicatorMap2 = indicatorMap2;
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap3() {
		return indicatorMap3;
	}

	public void setIndicatorMap3(Map<String, ArrayList<Indicator>> indicatorMap3) {
		this.indicatorMap3 = indicatorMap3;
	}

	public Rsi() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap1 = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap2 = new HashMap<String,ArrayList<Indicator>>();
		indicatorMap3 = new HashMap<String,ArrayList<Indicator>>();

	}
}
