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
	private float fastMacd = 0;
	private float slowMacd = 0;
	private float histogram = 0;
	
	private float dirMvmtUp;
	private float dirMvmtDown;
	private float trueRange;
	private float plusDI;
	private float minusDI;

	public Indicator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Indicator(Ticker t) {
		super(t.getSymbol(),t.getSeries(),t.getOpen(),t.getHigh(),t.getLow(),t.getClose(),t.getLast(),t.getPrevclose(),t.getTottrdqty(),(long) t.getTottrdval(),t.getTimestamp(),t.getTotaltrades(),t.getIsin());
		sma = 0;
		ema12 = 0;
		ema26 = 0;
		fastMacd = 0;
		slowMacd = 0;
		histogram =0;
		dirMvmtUp = 0;
		dirMvmtDown = 0;
		trueRange = 0;
		plusDI = 0;
		minusDI = 0;
	}

	public Indicator(String symbol, String series, float open, float high, float low, float close, float last,
			float prevclose, int tottrdqty, long tottrdval, String timestamp, String totalTrades,String isin) {
		super(symbol, series, open, high, low, close, last, prevclose, tottrdqty, tottrdval, timestamp, totalTrades,isin);
		// TODO Auto-generated constructor stub
	}

/*	@Override
	public String toString() {
		return "Ticker [symbol=" + getSymbol() + ", series=" + getSeries() + ", open="
				+ getOpen() + ", high=" + getHigh() + ", low=" + getLow() + ", close=" + getClose()
				+ ", last=" + getLast() + ", prevclose=" + getPrevclose()
				+ ", tottrdqty=" + getTottrdqty() + ", tottrdval=" + getTottrdval()
				+ ", timestamp=" + getTimestamp() + ", totaltrades=" + getTotaltrades()
				+ ", isin=" + getIsin() + ", Sma=" + sma + ", ema12=" + ema12 + ", ema26=" + ema26 + ", fastMacd=" + fastMacd +", slowMac="+ slowMacd + ", histogram=" + histogram + "]";
	}
*/
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

	public float getFastMacd() {
		return fastMacd;
	}

	public void setFastMacd(float fastMacd) {
		this.fastMacd = fastMacd;
	}

	public float getSlowMacd() {
		return slowMacd;
	}

	public void setSlowMacd(float slowMacd) {
		this.slowMacd = slowMacd;
	}

	public float getHistogram() {
		return histogram;
	}

	public void setHistogram(float histogram) {
		this.histogram = histogram;
	}

	public float getTrueRange() {
		return trueRange;
	}

	public void setTrueRange(float trueRange) {
		this.trueRange = trueRange;
	}

	public float getPlusDI() {
		return plusDI;
	}

	public void setPlusDI(float plusDI) {
		this.plusDI = plusDI;
	}

	public float getMinusDI() {
		return minusDI;
	}

	public void setMinusDI(float minusDI) {
		this.minusDI = minusDI;
	}

	@Override
	public String toString() {
		return "Ticker [symbol=" + getSymbol() + ", series=" + getSeries() + ", open="
				+ getOpen() + ", high=" + getHigh() + ", low=" + getLow() + ", close=" + getClose()
				+ ", last=" + getLast() + ", prevclose=" + getPrevclose()
				+ ", tottrdqty=" + getTottrdqty() + ", tottrdval=" + getTottrdval()
				+ ", timestamp=" + getTimestamp() + ", totaltrades=" + getTotaltrades()
				+ ", isin=" + getIsin() + ", sma=" + sma + ", ema12=" + ema12 + ", ema26=" + ema26 + ", fastMacd=" + fastMacd
				+ ", slowMacd=" + slowMacd + ", histogram=" + histogram + ", trueRange="
				+ trueRange + ", plusDI=" + plusDI + ", minusDI=" + minusDI + "]";
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
}
