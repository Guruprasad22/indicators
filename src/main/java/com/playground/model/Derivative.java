package com.playground.model;

public class Derivative {
	
	private String date;
	private String isin;
	private String scrip_name;
	private String nse_symbol;
	private int mwpl;
	private int open_interest;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getScrip_name() {
		return scrip_name;
	}
	public void setScrip_name(String scrip_name) {
		this.scrip_name = scrip_name;
	}
	public String getSymbol() {
		return nse_symbol;
	}
	public void setSymbol(String symbol) {
		this.nse_symbol = symbol;
	}
	public int getMwpl() {
		return mwpl;
	}
	public void setMwpl(int mwpl) {
		this.mwpl = mwpl;
	}
	public int getOpen_interest() {
		return open_interest;
	}
	public void setOpen_interest(int open_interest) {
		this.open_interest = open_interest;
	}
	@Override
	public String toString() {
		return "Derivative [date=" + date + ", isin=" + isin + ", scrip_name=" + scrip_name + ", symbol=" + nse_symbol
				+ ", mwpl=" + mwpl + ", open_interest=" + open_interest + "]";
	}
	public Derivative() {
		super();
	}
	public Derivative(String date, String isin, String scrip_name, String symbol, int mwpl, int open_interest) {
		super();
		this.date = date;
		this.isin = isin;
		this.scrip_name = scrip_name;
		this.nse_symbol = symbol;
		this.mwpl = mwpl;
		this.open_interest = open_interest;
	}
}
