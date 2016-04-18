package com.platform.mongo.util;

import java.text.DecimalFormat;

public class Format {
	private static final String zero_5 = "00000";

	public static String format5(int value) {
		DecimalFormat df = new DecimalFormat(zero_5);
		return df.format(value);
	}
}
