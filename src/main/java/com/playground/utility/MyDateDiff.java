package com.playground.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.playground.model.Ticker;

public class MyDateDiff {

	public static int doDataDiff(String d1,String d2) throws ParseException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = simpleDateFormat.parse(d1);
		Date date1 = simpleDateFormat.parse(d2);
		if(date.compareTo(date1) < 0) {
			return -1;
		} else {
			return ((int)( (date1.getTime() - date.getTime()) / (1000 * 60 * 60 * 24)));
		}
	}
}

