package com.playground.utility;

import org.apache.log4j.Logger;

import com.playground.model.Derivative;

public class OpenIntToPojo {
	
	private Derivative derivative;
	private static Logger log = Logger.getLogger(OpenIntToPojo.class);
	
	public Derivative doConvert(String line) {
		String[] record = line.split(",");
		derivative.setDate(record[0]);
		derivative.setIsin(record[1]);
		derivative.setScrip_name(record[2]);
		derivative.setSymbol(record[3]);
		derivative.setMwpl(Integer.parseInt(record[4]));
		derivative.setOpen_interest(Integer.parseInt(record[5]));
		return derivative;
	}

	public Derivative getDerivative() {
		return derivative;
	}

	public void setDerivative(Derivative derivative) {
		this.derivative = derivative;
	}

	public OpenIntToPojo() {
		super();
		derivative = new Derivative();
	}
}
