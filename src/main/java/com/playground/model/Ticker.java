package com.playground.model;

/**
 * 
 * @author Guruprasad Bhaskar 
 * Pojo for BhavCopy
 *
 */

public class Ticker {
	private String symbol;
	private String series;
	private float open;
	private float high;
	private float low;
	private float close;
	private float last;
	private float prevclose;
	private int tottrdqty;
	private float tottrdval;
	private String timestamp;
	private String totaltrades;
	private String isin;
	
	public Ticker(String symbol, String series, float open, float high,
			float low, float close, float last, float prevclose, int tottrdqty,
			long tottrdval, String timestamp, String isin) {
		super();
		this.symbol = symbol;
		this.series = series;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.last = last;
		this.prevclose = prevclose;
		this.tottrdqty = tottrdqty;
		this.tottrdval = tottrdval;
		this.timestamp = timestamp;
		this.isin = isin;
	}
	
	public Ticker() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getLast() {
		return last;
	}
	public void setLast(float last) {
		this.last = last;
	}
	public float getPrevclose() {
		return prevclose;
	}
	public void setPrevclose(float prevclose) {
		this.prevclose = prevclose;
	}
	public int getTottrdqty() {
		return tottrdqty;
	}

	public void setTottrdqty(int tottrdqty) {
		this.tottrdqty = tottrdqty;
	}
	public float getTottrdval() {
		return tottrdval;
	}
	public void setTottrdval(float tottrdval) {
		this.tottrdval = tottrdval;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getTotaltrades() {
		return totaltrades;
	}

	public void setTotaltrades(String totaltrades) {
		this.totaltrades = totaltrades;
	}

	@Override
	public String toString() {
		return "Ticker [symbol=" + symbol + ", series=" + series + ", open="
				+ open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", last=" + last + ", prevclose=" + prevclose
				+ ", tottrdqty=" + tottrdqty + ", tottrdval=" + tottrdval
				+ ", timestamp=" + timestamp + ", totaltrades=" + totaltrades
				+ ", isin=" + isin + "]";
	}
	
}

