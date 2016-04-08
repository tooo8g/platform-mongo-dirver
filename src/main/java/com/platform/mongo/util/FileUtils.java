package com.platform.mongo.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {
	/**
	 * 得到文件名称 去掉路径和后缀
	 * 
	 * @auth zhanglei
	 * @param fileUri
	 * @return
	 */
	public static String getFileName(String fileUri) {
		File f = new File(fileUri);
		String filename = f.getName().replaceAll("[.][^.]+$", "");
		return filename;
	}

	/**
	 * 得到文件夹下的文件列表
	 * 
	 * @auth zhanglei
	 * @param dir
	 *            文件夹路径
	 * @param dex
	 *            文件后缀过滤
	 * @return
	 */
	public static String[] getFilesFormDirectory(String dir, final String dex) {
		File f = new File(dir);
		if (f.isDirectory()) {
			String[] files = f.list(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					if (name.toLowerCase().matches("^\\S+\\." + dex + "$"))
						return true;
					return false;
				}
			});

			return files;
		}

		return null;
	}

}
