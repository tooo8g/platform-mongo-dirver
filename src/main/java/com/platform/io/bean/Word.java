package com.platform.io.bean;

public class Word {

	public String str;//关键字
	
	public double w = 1.0;//权重（默认是1）

	public Word(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}
}
