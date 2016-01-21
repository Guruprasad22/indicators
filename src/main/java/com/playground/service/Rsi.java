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
			Collections.sort(indicatorList,new MyTickerComparator());
			if(indicatorList.size() >= 14) {
				for(int i=1; i<indicatorList.size(); i++) {
					Indicator indicator = indicatorList.get(i);
					if(indicator.getClose() >= indicatorList.get(i-1).getClose()) {
						indicator.setGain(new BigDecimal(indicator.getClose() - indicatorList.get(i-1).getClose()));
						indicator.setLoss(new BigDecimal(0.000));
					} else {
						indicator.setLoss(new BigDecimal(indicatorList.get(i-1).getClose() - indicator.getClose()));
						indicator.setGain(new BigDecimal(0.000));
					}
					if(i==1) {
						MapUtil.updateIndicatorMap(entry.getKey(), indicatorList.get(i-1), indicatorMap1);
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
			Collections.sort(indicatorList,new MyTickerComparator());
			BigDecimal netGain = new BigDecimal(0.000);
			BigDecimal netLoss = new BigDecimal(0.000);
			if(indicatorList.size() >= 14) {
				
				for(int i=0; i<13; i++) {
					Indicator indicator = indicatorList.get(i);
					MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
					if(i==0) 
						continue; // no need to calculate the avgGain and Loss for first ticker
					netGain = netGain.add(indicator.getLoss());
					netLoss = netLoss.add(indicator.getGain());
					if(i ==12) {
						Indicator ind = indicatorList.get(13);
						ind.setAvgGain(netGain.divide(new BigDecimal(14),3,RoundingMode.HALF_UP));
						ind.setAvgLoss(netLoss.divide(new BigDecimal(14),3,RoundingMode.HALF_UP));
						MapUtil.updateIndicatorMap(entry.getKey(), ind, indicatorMap2);
					}
				}
				

				for(int i=14;i< indicatorList.size();i++) {
					if(i ==14) {
						netGain = netGain.multiply(new BigDecimal(13)).add(indicatorList.get(i).getGain()).divide(new BigDecimal(14),3,RoundingMode.HALF_UP);
						netLoss = netLoss.multiply(new BigDecimal(13)).add(indicatorList.get(i).getLoss()).divide(new BigDecimal(14),3,RoundingMode.HALF_UP);
						Indicator indicator = indicatorList.get(i);
						indicator.setAvgGain(netGain);
						indicator.setAvgLoss(netLoss);
						MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
						continue;
					}
					Indicator indicator = indicatorList.get(i);
					netGain =  indicatorList.get(i-1).getAvgGain().multiply(new BigDecimal(13)).add(indicator.getGain()).divide(new BigDecimal(14),3,RoundingMode.HALF_UP);
					netLoss =  indicatorList.get(i-1).getAvgLoss().multiply(new BigDecimal(13)).add(indicator.getLoss()).divide(new BigDecimal(14),3,RoundingMode.HALF_UP);
					indicator.setAvgGain(netGain);
					indicator.setAvgLoss(netLoss);
					MapUtil.updateIndicatorMap(entry.getKey(), indicator, indicatorMap2);
				}				
			}

		}
		indicatorMap1.clear();
		//finally calculate RSI
		entries = indicatorMap2.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());
			if(indicatorList.size() >= 14) {
				for(int i=0;i<13;i++) {
					MapUtil.updateIndicatorMap(entry.getKey(),indicatorList.get(i),indicatorMap3);
				}
				BigDecimal rsi = new BigDecimal("0.000");
				for(int i=13;i<indicatorList.size();i++) {
					Indicator indicator = indicatorList.get(i);
					if(indicator.getAvgLoss().equals(new BigDecimal("0.000"))) {
						indicator.setRsi(new BigDecimal("100.000"));
					} else {
						rsi = new BigDecimal("100.000").subtract((new BigDecimal("100.000").divide((new BigDecimal("1.000").add(indicator.getAvgGain().divide(indicator.getAvgLoss(),3,RoundingMode.HALF_UP))),3,RoundingMode.HALF_UP)));
						indicator.setRsi(rsi);
					}
					MapUtil.updateIndicatorMap(entry.getKey(),indicator,indicatorMap3);
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
