package com.upstox.ohlc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to represent stock detail. 
 * @author Bipin Anghan
 *
 */
public class Stock {

	private String	sym;
	@JsonProperty("P")
	private double	P;		//Price of Trade

	@JsonProperty("Q")
	private double	Q;		//Quantity Traded

	@JsonProperty("TS2")
	private long	TS2;	//Timestamp in UTC uint64

	public Stock() {}

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}

	public double getP() {
		return P;
	}

	public void setP(double p) {
		P = p;
	}

	public double getQ() {
		return Q;
	}

	public void setQ(double q) {
		Q = q;
	}

	public long getTS2() {
		return TS2;
	}

	public void setTS2(long tS2) {
		TS2 = tS2;
	}

	@Override
	public String toString() {
		return "{sym:" + sym + ", P:" + P + ", Q:" + Q + ", TS2:" + TS2 + "}";
	}

}
