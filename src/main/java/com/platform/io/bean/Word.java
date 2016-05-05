package com.platform.io.bean;

import com.platform.mongo.util.WordWeighUtil;

public class Word {

	public String str;//关键字
	
	public double w = 1.0;//权重（默认是1）

	public Word(String str) {
		if(str.contains("#")){
			String s[] = str.split("#");
			System.out.println("词频："+s[1]);
			this.str = s[0];
			double  qz = WordWeighUtil.W(Double.valueOf(s[1]));
			System.out.println("权重："+qz);
			this.w = qz;
		}
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
