package com.platform.mongo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant {

	public static String uri;

	static {
		try {
			Properties properties = new Properties();
			InputStream inputstream = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("mongo.properties");
			properties.load(inputstream);
			inputstream.close();

			uri = properties.getProperty("uri");
		} catch (IOException e) {

		}
	}

	/**
	 * 总权重(w>1才能为true)
	 */
	public static double w = 1.1;
	/**
	 * 2.5个词被检索到才取出此条数据
	 */
	public static double x = 1.8;

}
