package com.playground.utility;

import com.playground.model.Ticker;

public class StringToPojo {
	
	private Ticker ticker;
	
	public Ticker convertToPojo(String source) {
		int i = 0;
		String[] unit = source.split(",");
		ticker.setSymbol(unit[i++]);
		ticker.setSeries(unit[i++]);
		ticker.setOpen(Float.parseFloat(unit[i++]));
		ticker.setHigh(Float.parseFloat(unit[i++]));
		ticker.setLow(Float.parseFloat(unit[i++]));
		ticker.setClose(Float.parseFloat(unit[i++]));
		ticker.setLast(Float.parseFloat(unit[i++]));
		ticker.setPrevclose(Float.parseFloat(unit[i++]));
		ticker.setTottrdqty(Integer.parseInt(unit[i++]));
		ticker.setTottrdval(Float.parseFloat(unit[i++]));
		ticker.setTimestamp(unit[i++]);
		ticker.setTotaltrades(unit[i++]);
		ticker.setIsin(unit[i]);
		return ticker;
	}

	public StringToPojo(Ticker ticker) {
		super();
		this.ticker = ticker;
	}

	public StringToPojo() {
		super();
		ticker =  new Ticker();
		// TODO Auto-generated constructor stub
	}

	public Ticker getTicker() {
		return ticker;
	}

	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
}