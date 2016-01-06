package com.playground.model;

public class Indicator extends Ticker {
	
	public float getSma() {
		return sma;
	}

	public void setSma(float sma) {
		this.sma = sma;
	}

	private float sma = 0;
	private float ema12 = 0;
	private float ema26 = 0;

	public Indicator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Indicator(Ticker t) {
		super(t.getSymbol(),t.getSeries(),t.getOpen(),t.getHigh(),t.getLow(),t.getClose(),t.getLast(),t.getPrevclose(),t.getTottrdqty(),(long) t.getTottrdval(),t.getTimestamp(),t.getIsin());
		sma = 0;
	}

	public Indicator(String symbol, String series, float open, float high, float low, float close, float last,
			float prevclose, int tottrdqty, long tottrdval, String timestamp, String isin) {
		super(symbol, series, open, high, low, close, last, prevclose, tottrdqty, tottrdval, timestamp, isin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Ticker [symbol=" + getSymbol() + ", series=" + getSeries() + ", open="
				+ getOpen() + ", high=" + getHigh() + ", low=" + getLow() + ", close=" + getClose()
				+ ", last=" + getLast() + ", prevclose=" + getPrevclose()
				+ ", tottrdqty=" + getTottrdqty() + ", tottrdval=" + getTottrdval()
				+ ", timestamp=" + getTimestamp() + ", totaltrades=" + getTotaltrades()
				+ ", isin=" + getIsin() + ", Sma=" + sma + ", ema12=" + ema12 + ", ema26=" + ema26 +   "]";
	}

	public float getEma12() {
		return ema12;
	}

	public void setEma12(float ema12) {
		this.ema12 = ema12;
	}

	public float getEma26() {
		return ema26;
	}

	public void setEma26(float ema26) {
		this.ema26 = ema26;
	}
}
