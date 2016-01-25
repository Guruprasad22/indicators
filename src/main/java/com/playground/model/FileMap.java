package com.playground.model;

public class FileMap {
	
	private String name;
	private String ticker;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public FileMap(String name, String ticker) {
		super();
		this.name = name;
		this.ticker = ticker;
	}
	
	public FileMap() {
		super();
	}
}
