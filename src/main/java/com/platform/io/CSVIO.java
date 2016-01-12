package com.platform.io;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.platform.io.bean.TreeStruct;
import com.platform.io.bean.SteelPrice;
import com.platform.mongo.util.PinYin;

public class CSVIO {

	/**
	 * 读取钢价格csv
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<SteelPrice> readSteelPrice(String fileName) {
		CsvReader r = null;
		List<SteelPrice> sp = new ArrayList<SteelPrice>();
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			r.readHeaders();
			while (r.readRecord()) {
				SteelPrice steelprice = new SteelPrice();
				steelprice.setName(r.get("PM1"));
				steelprice.setSpecification(r.get("GG1"));
				steelprice.setTexture(r.get("CZH1"));
				steelprice.setCompany(r.get("CHD"));
				steelprice.setArea(r.get("QY"));
				steelprice.setCity(r.get("CHSH"));
				steelprice.setDate(r.get("JGRQ"));
				steelprice.setPrice(r.get("JG"));
				steelprice.setExpand(r.get("MEMO"));
				sp.add(steelprice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.close();
		}
		return sp;
	}

	/**
	 * 读取资质-铁路总公司铁路专用产品认证采信csv
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<TreeStruct> readCertification_TZ(String fileName) {
		CsvReader r = null;
		List<TreeStruct> certs = new ArrayList<TreeStruct>();
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			r.readHeaders();
			while (r.readRecord()) {
				TreeStruct cert = new TreeStruct();
				cert.setId(r.get("ID"));
				cert.setName("name");
				cert.setValue(r.get("VAL"));
				cert.setPid(r.get("PID"));
				cert.setPy_char(PinYin.getPinYinHeadChar(cert.getValue())
						.charAt(0));
				certs.add(cert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.close();
		}
		return certs;
	}
}
