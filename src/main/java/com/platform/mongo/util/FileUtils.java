package com.platform.mongo.util;

import java.io.File;

public class FileUtils {
	public  static String getFileName(String fileUri){
		 File f = new File(fileUri);
	      String  filename = f.getName().replaceAll("[.][^.]+$", "");
		 return filename;
		}
}
