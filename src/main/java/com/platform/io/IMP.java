package com.platform.io;

import java.util.List;

import com.platform.io.bean.Standardization;
import com.platform.mongo.s1.MongoDirver;

public class IMP {

	public void impStandard(String fileName) {
		List<Standardization> stands = MSIO.readStandardizations(fileName);
		MongoDirver md = new MongoDirver();
		for (Standardization stand : stands) {
			md.addLatestStandards(stand);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMP imp = new IMP();
		imp.impStandard("d://test//standards.xls");

	}

}
