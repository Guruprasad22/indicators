package com.playground.model;

import java.math.BigDecimal;

public class Indicator extends Ticker {
	
	public BigDecimal getSma() {
		return sma;
	}

	public void setSma(BigDecimal sma) {
		this.sma = sma;
	}

	private BigDecimal sma;
	private BigDecimal ema12;
	private BigDecimal ema26;
	private BigDecimal fastMacd;
	private BigDecimal slowMacd;
	private BigDecimal histogram;
	
	//adx related
	private float dirMvmtUp;
	private float dirMvmtDown;
	private float trueRange;
	private float plusDI;
	private float minusDI;
	private float tr14;
	private float plusDM14;
	private float minusDM14;
	private float plusDI14;
	private float minusDI14;
	private float dx;
	private float adx;
	
	//rsi related
	private BigDecimal gain;
	private BigDecimal loss;
	private BigDecimal avgGain;
	private BigDecimal avgLoss;
	private BigDecimal rsi;
	
	//on balance volume
	private long obv;
	
	//accumulation distribution
	private BigDecimal ad;
	
	//force index
	private BigDecimal fi;
	private BigDecimal fi13;

	public Indicator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Indicator(Ticker t) {
		super(t.getSymbol(),t.getSeries(),t.getOpen(),t.getHigh(),t.getLow(),t.getClose(),t.getLast(),t.getPrevclose(),t.getTottrdqty(),(long) t.getTottrdval(),t.getTimestamp(),t.getTotaltrades(),t.getIsin());
		sma = new BigDecimal("0.000");
		ema12 = new BigDecimal("0.000");
		ema26 = new BigDecimal("0.000");
		fastMacd = new BigDecimal("0.000");
		slowMacd = new BigDecimal("0.000");
		histogram =new BigDecimal("0.000");
		dirMvmtUp = 0;
		dirMvmtDown = 0;
		trueRange = 0;
		plusDI = 0;
		minusDI = 0;
		tr14 = 0;
		plusDM14 = 0;
		minusDM14 = 0;
		plusDI14 = 0;
		minusDI14 = 0;
		dx = 0;
		adx = 0;
		gain = new BigDecimal("0.000");
		loss = new BigDecimal("0.000");
		avgGain = new BigDecimal("0.000");
		avgLoss = new BigDecimal("0.000");
		rsi = new BigDecimal("0.000");
		obv = 0;
		ad = new BigDecimal("0.0");
		fi = new BigDecimal("0.0");
		fi13 = new BigDecimal("0.0");
	}

	public Indicator(String symbol, String series, float open, float high, float low, float close, float last,
			float prevclose, int tottrdqty, long tottrdval, String timestamp, String totalTrades,String isin) {
		super(symbol, series, open, high, low, close, last, prevclose, tottrdqty, tottrdval, timestamp, totalTrades,isin);
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getEma12() {
		return ema12;
	}

	public void setEma12(BigDecimal ema12) {
		this.ema12 = ema12;
	}

	public BigDecimal getEma26() {
		return ema26;
	}

	public void setEma26(BigDecimal ema26) {
		this.ema26 = ema26;
	}

	public BigDecimal getFastMacd() {
		return fastMacd;
	}

	public void setFastMacd(BigDecimal fastMacd) {
		this.fastMacd = fastMacd;
	}

	public BigDecimal getSlowMacd() {
		return slowMacd;
	}

	public void setSlowMacd(BigDecimal slowMacd) {
		this.slowMacd = slowMacd;
	}

	public BigDecimal getHistogram() {
		return histogram;
	}

	public void setHistogram(BigDecimal histogram) {
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

	public float getTr14() {
		return tr14;
	}

	public void setTr14(float tr14) {
		this.tr14 = tr14;
	}

	public float getPlusDM14() {
		return plusDM14;
	}

	public void setPlusDM14(float plusDM14) {
		this.plusDM14 = plusDM14;
	}

	public float getMinusDM14() {
		return minusDM14;
	}

	public void setMinusDM14(float minusDM14) {
		this.minusDM14 = minusDM14;
	}

	public float getPlusDI14() {
		return plusDI14;
	}

	public void setPlusDI14(float plusDI14) {
		this.plusDI14 = plusDI14;
	}

	public float getMinusDI14() {
		return minusDI14;
	}

	public void setMinusDI14(float minusDI14) {
		this.minusDI14 = minusDI14;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getAdx() {
		return adx;
	}

	public void setAdx(float adx) {
		this.adx = adx;
	}

	@Override
	public String toString() {
		return "Ticker [symbol=" + getSymbol() + ", series=" + getSeries() + ", open="
				+ getOpen() + ", high=" + getHigh() + ", low=" + getLow() + ", close=" + getClose()
				+ ", last=" + getLast() + ", prevclose=" + getPrevclose()
				+ ", tottrdqty=" + getTottrdqty() + ", tottrdval=" + getTottrdval()
				+ ", timestamp=" + getTimestamp() + ", totaltrades=" + getTotaltrades()
				+ ", isin=" + getIsin() + " sma=" + sma + ", ema12=" + ema12 + ", ema26=" + ema26 + ", fastMacd=" + fastMacd
				+ ", slowMacd=" + slowMacd + ", histogram=" + histogram + ", dirMvmtUp=" + dirMvmtUp + ", dirMvmtDown="
				+ dirMvmtDown + ", trueRange=" + trueRange + ", plusDI=" + plusDI + ", minusDI=" + minusDI + ", tr14="
				+ tr14 + ", plusDM14=" + plusDM14 + ", minusDM14=" + minusDM14 + ", plusDI14=" + plusDI14
				+ ", minusDI14=" + minusDI14 + ", dx=" + dx + ", adx=" + adx + ", gain=" + gain + ", loss=" + loss
				+ ", avgGain=" + avgGain + ", avgLoss=" + avgLoss + ", rsi=" + rsi +  ", obv=" + obv + ", ad=" + ad + ", fi=" + fi +  ", fi13=" + fi13 +"]";
	}

	public BigDecimal getGain() {
		return gain;
	}

	public void setGain(BigDecimal gain) {
		this.gain = gain;
	}

	public BigDecimal getLoss() {
		return loss;
	}

	public void setLoss(BigDecimal loss) {
		this.loss = loss;
	}

	public BigDecimal getAvgGain() {
		return avgGain;
	}

	public void setAvgGain(BigDecimal avgGain) {
		this.avgGain = avgGain;
	}

	public BigDecimal getAvgLoss() {
		return avgLoss;
	}

	public void setAvgLoss(BigDecimal avgLoss) {
		this.avgLoss = avgLoss;
	}

	public BigDecimal getRsi() {
		return rsi;
	}

	public void setRsi(BigDecimal rsi) {
		this.rsi = rsi;
	}

	public long getObv() {
		return obv;
	}

	public void setObv(long obv) {
		this.obv = obv;
	}

	public BigDecimal getAd() {
		return ad;
	}

	public void setAd(BigDecimal ad) {
		this.ad = ad;
	}

	public BigDecimal getFi() {
		return fi;
	}

	public void setFi(BigDecimal fi) {
		this.fi = fi;
	}

	public BigDecimal getFi13() {
		return fi13;
	}

	public void setFi13(BigDecimal fi13) {
		this.fi13 = fi13;
	}
}
