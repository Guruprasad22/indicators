package com.playground.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.playground.model.Indicator;
import com.playground.utility.MapUtil;
import com.playground.utility.MyTickerComparator;

public class Adx {
	
	private static Logger log = Logger.getLogger(Adx.class);
	private Map<String,ArrayList<Indicator>> indicatorMap;
	private Map<String,ArrayList<Indicator>> tempIndicatorMap;
	private Map<String,ArrayList<Indicator>> tempIndicatorMap1;
	private float dirMvmtUp;
	private float dirMvmtDown;
	private float trueRange;
	private float plusDi;
	private float minusDi;
	
	public Map<String,ArrayList<Indicator>> doAdx() {
		
		Iterator<Entry<String, ArrayList<Indicator>>> entries = indicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) {
			
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());
			for(int i=1; i<indicatorList.size(); i++) {
				Indicator indicator = indicatorList.get(i);
				
				//calculate the true range
				float[] boundaries = new float[3];
				boundaries[0] =indicator.getHigh() - indicator.getLow();
				boundaries[1] = Math.abs(indicator.getHigh() - indicatorList.get(i-1).getClose());
				boundaries[2] = Math.abs(indicator.getLow() - indicatorList.get(i-1).getClose());
				List b = Arrays.asList(ArrayUtils.toObject(boundaries));
				trueRange = (Float)Collections.max(b);

				
				//calculate the  directional movement
				if((indicator.getHigh() - indicatorList.get(i-1).getHigh()) > (indicatorList.get(i-1).getLow() - indicator.getLow())) {
					if(indicator.getHigh() - indicatorList.get(i-1).getHigh() > 0) {
						dirMvmtUp = indicator.getHigh() - indicatorList.get(i-1).getHigh();
					} else {
						dirMvmtUp = 0;
					}
					dirMvmtDown = 0;
				}else if((indicatorList.get(i-1).getLow() - indicator.getLow()) > (indicator.getHigh() - indicatorList.get(i-1).getHigh())) {
					if((indicatorList.get(i-1).getLow() - indicator.getLow()) > 0)	 {
						dirMvmtDown = (indicatorList.get(i-1).getLow() - indicator.getLow());
					} else {
						dirMvmtDown = 0;
					}
					dirMvmtUp = 0;
				}
				
				indicator.setTrueRange(trueRange);
				indicator.setDirMvmtUp(dirMvmtUp);
				indicator.setDirMvmtDown(dirMvmtDown);
				MapUtil.updateIndicatorMap(entry.getKey(),indicator,tempIndicatorMap);
				
				
			}
		}
		
		entries = tempIndicatorMap.entrySet().iterator();
		
		while(entries.hasNext()) {
			
			Map.Entry<String, ArrayList<Indicator>> entry = entries.next();
			ArrayList<Indicator> indicatorList = entry.getValue();
			Collections.sort(indicatorList,new MyTickerComparator());
			float tr14 = 0;
			for(int i=0; i<12;i++) {
				Indicator indicator = indicatorList.get(i);
				tr14 = tr14 + indicator.getTrueRange();
				MapUtil.updateIndicatorMap(entry.getKey(),indicator,tempIndicatorMap);
			}
			Indicator indicator = indicatorList.get(12);
			indicator.setTr14((float) tr14/13);
			MapUtil.updateIndicatorMap(entry.getKey(),indicator,tempIndicatorMap);
			
			for(int i=13; i < indicatorList.size(); i++) {
				
			}
		}
		
		
		
		return tempIndicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getIndicatorMap() {
		return indicatorMap;
	}

	public void setIndicatorMap(Map<String, ArrayList<Indicator>> indicatorMap) {
		this.indicatorMap = indicatorMap;
	}

	public Map<String, ArrayList<Indicator>> getTempIndicatorMap() {
		return tempIndicatorMap;
	}

	public void setTempIndicatorMap(Map<String, ArrayList<Indicator>> tempIndicatorMap) {
		this.tempIndicatorMap = tempIndicatorMap;
	}

	public float getDirMvmtUp() {
		return dirMvmtUp;
	}

	public void setDirMvmtUp(float dirMvmtUp) {
		this.dirMvmtUp = dirMvmtUp;
	}

	public float getDirMvmtDown() {
		return dirMvmtDown;
	}

	public void setDirMvmtDown(float dirMvmtDown) {
		this.dirMvmtDown = dirMvmtDown;
	}

	public float getTrueRange() {
		return trueRange;
	}

	public void setTrueRange(float trueRange) {
		this.trueRange = trueRange;
	}

	public float getPlusDi() {
		return plusDi;
	}

	public void setPlusDi(float plusDi) {
		this.plusDi = plusDi;
	}

	public float getMinusDi() {
		return minusDi;
	}

	public void setMinusDi(float minusDi) {
		this.minusDi = minusDi;
	}

	public Adx() {
		super();
		indicatorMap = new HashMap<String,ArrayList<Indicator>>();
		tempIndicatorMap = new HashMap<String,ArrayList<Indicator>>();
		tempIndicatorMap1 = new HashMap<String,ArrayList<Indicator>>();
	}

	public Map<String, ArrayList<Indicator>> getTempIndicatorMap1() {
		return tempIndicatorMap1;
	}

	public void setTempIndicatorMap1(Map<String, ArrayList<Indicator>> tempIndicatorMap1) {
		this.tempIndicatorMap1 = tempIndicatorMap1;
	}
}
