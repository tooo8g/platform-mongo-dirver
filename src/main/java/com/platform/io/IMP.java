package com.platform.io;

import java.util.List;

import com.platform.io.bean.Certification;
import com.platform.io.bean.Standardization;
import com.platform.io.bean.SteelPrice;
import com.platform.mongo.s1.MongoDirver;

public class IMP {

	public void impStandard(String fileName) {
		List<Standardization> stands = MSIO.readStandardizations(fileName);
		MongoDirver md = new MongoDirver();
		for (Standardization stand : stands) {
			md.addLatestStandards(stand);
		}
	}

	public void impCertification(String fileName) {
		List<Certification> certs = MSIO.readCertification(fileName);
		MongoDirver md = new MongoDirver();
		for (Certification cert : certs) {
			md.addCertification(cert);
		}
	}

	public void impSteelPrice(String fileName) {
		List<SteelPrice> steelprices = CSVIO.readSteelPrice(fileName);
		MongoDirver md = new MongoDirver();
		for (SteelPrice sp : steelprices) {
			md.addSteelPrice(sp);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMP imp = new IMP();
		imp.impCertification("d://test//certification.xls");

	}

}
