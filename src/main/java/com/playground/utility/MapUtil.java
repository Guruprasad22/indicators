package com.playground.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.playground.model.Indicator;

public class MapUtil {
	
	private static Logger log = Logger.getLogger(MapUtil.class);
	
	public static void printMap(Map<String, ArrayList<Indicator>> myMap) {
		
		Iterator<Entry<String,ArrayList<Indicator>>> iterator = myMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = iterator.next();
			log.debug("Key is " + entry.getKey());
			ArrayList<Indicator> myList = entry.getValue();
			for(Object obj : myList) {
					log.debug(obj );
			}
		}
	}
	
	public static ArrayList<Indicator> compileList(Map<String, ArrayList<Indicator>> myMap) {
		
		Iterator<Entry<String,ArrayList<Indicator>>> iterator = myMap.entrySet().iterator();
		ArrayList<Indicator> myList = new ArrayList<Indicator>();
		while(iterator.hasNext()) {
			Map.Entry<String, ArrayList<Indicator>> entry = iterator.next();
			myList.addAll(entry.getValue());
		}
		return myList;
	}
}
