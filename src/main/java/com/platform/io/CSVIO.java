package com.platform.io;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.platform.io.bean.SteelPrice;

public class CSVIO {

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
}
