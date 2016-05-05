package com.platform.mongo.util;


public class WordWeighUtil {

	private static final double x = 100.0;

	/**
	 * 获取词的权重
	 * 
	 * @auth zhanglei
	 * @param count
	 *            词频
	 * @return
	 */
	public static double W(double count) {
		double w = 0;
		if (count > 0) {
			w = x / count;
		}

		return w;
	}

	
	public static void main(String[] args){
		System.out.println(WordWeighUtil.W(9308));
	}
}
