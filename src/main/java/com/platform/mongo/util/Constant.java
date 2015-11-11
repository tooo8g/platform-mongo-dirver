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

}
