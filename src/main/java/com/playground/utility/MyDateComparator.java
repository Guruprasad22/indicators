package com.playground.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.playground.model.Ticker;

public class MyDateComparator implements Comparator<Ticker> {

	public int compare(Ticker o1, Ticker o2){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		Date date1 = new Date();
		try {
			date = simpleDateFormat.parse(o1.getTimestamp());
			date1 = simpleDateFormat.parse(o2.getTimestamp());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(date.compareTo(date1) < 0) {
			return 11;
		} else {
			return -1;
		}
	}
}