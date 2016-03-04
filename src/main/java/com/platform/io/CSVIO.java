package com.platform.io;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.platform.io.bean.ProductInfo;
import com.platform.io.bean.TreeStruct;
import com.platform.io.bean.Price;
import com.platform.mongo.util.PinYin;

public class CSVIO {

	/**
	 * 读取价格csv
	 * 
	 * @param fileName
	 * @return
	 */
	public static List<Price> readPrice(String fileName) {
		CsvReader r = null;
		List<Price> sp = new ArrayList<Price>();
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			r.readHeaders();
			while (r.readRecord()) {
				Price price = new Price();
				price.setName(r.get("PM1"));
				price.setSpecification(r.get("GG1"));
				price.setTexture(r.get("CZH1"));
				price.setCompany(r.get("CHD"));
				price.setArea(r.get("QY"));
				price.setCity(r.get("CHSH"));
				price.setDate(r.get("JGRQ"));
				price.setPrice(r.get("JG"));
				price.setExpand(r.get("MEMO"));
				price.setPriceType(r.get("JJFSH"));
				sp.add(price);
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
	/**
	 * 读取产品
	 * @param fileName
	 * @return
	 * @author zhangyb
	 */
	public static List<ProductInfo> readProductInfo(String fileName){
		CsvReader r = null;
		List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
		try {
			r = new CsvReader(fileName, ',', Charset.forName("UTF-8"));
			r.readHeaders();
			while (r.readRecord()) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setCompany_name(r.get("VAL"));
				productInfo.setProduct_identify(r.get("PID"));
				productInfo.setProduct_name(r.get("DIV"));
				productInfo.setSpecification(r.get("DIV"));
				productInfo.setMeasurement(r.get("DIV"));
				productInfo.setMaterial_code(r.get("DIV"));
				productInfo.setPurchasing_company(r.get("DIV"));
				productInfos.add(productInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
				r.close();
		}
		return productInfos;
	}
}
