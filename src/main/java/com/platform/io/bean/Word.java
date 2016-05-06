package com.platform.io.bean;

import java.math.BigDecimal;

public class Word {

	public String str;// 关键字

	public BigDecimal count;// 词频

	public BigDecimal w = new BigDecimal(1.0);// 权重（默认是1）

	public BigDecimal per;// 词频在语句中占的百分比

	public BigDecimal hz_per;// 词频比重

	public Word(String str, String count) {
		this.str = str;
		this.count = new BigDecimal(count);
	}

	public String getStr() {
		return str;
	}

	public double getW() {
		return w.doubleValue();
	}

	public void setW(BigDecimal w) {
		this.w = w;
	}

	public BigDecimal getCount() {
		return count;
	}

	public BigDecimal getPer() {
		return per;
	}

	public void setPer(BigDecimal per) {
		this.per = per;
	}

	public BigDecimal getHz_per() {
		return hz_per;
	}

	public void setHz_per(BigDecimal hz_per) {
		this.hz_per = hz_per;
	}

	@Override
	public String toString() {
		return "Word [str=" + str + ", count=" + count.doubleValue() + ", w="
				+ w.doubleValue() + ", per=" + per.doubleValue() + ", hz_per="
				+ hz_per.doubleValue() + "]";
	}
}
