package com.platform.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.platform.io.bean.Certification;
import com.platform.io.bean.Standardization;
import com.platform.io.bean.SteelPrice;
import com.platform.io.bean.TreeStruct;
import com.platform.mongo.s1.MongoDirver;
import com.platform.mongo.util.Tree;

public class IMP {

	/**
	 * 认证
	 * 
	 * @param fileName
	 */
	public void impStandard(String fileName) {
		List<Standardization> stands = MSIO.readStandardizations(fileName);
		MongoDirver md = new MongoDirver();
		for (Standardization stand : stands) {
			md.addLatestStandards(stand);
		}
	}

	/**
	 * 资质
	 * 
	 * @param fileName
	 */
	public void impCertification(String fileName) {
		List<Certification> certs = MSIO.readCertification(fileName);
		MongoDirver md = new MongoDirver();
		for (Certification cert : certs) {
			md.addCertification(cert);
		}
	}

	/**
	 * 钢价格
	 * 
	 * @param fileName
	 */
	public void impSteelPrice(String fileName) {
		List<SteelPrice> steelprices = CSVIO.readSteelPrice(fileName);
		MongoDirver md = new MongoDirver();
		for (SteelPrice sp : steelprices) {
			md.addSteelPrice(sp);
		}
	}

	/**
	 * 资质-铁路总公司铁路专用产品认证采信
	 * 
	 * @param fileName
	 */
	public void impCertification_TZ(String fileName) {
		List<TreeStruct> certs = CSVIO.readCertification_TZ(fileName);
		Map<String, List<TreeStruct>> pids = new HashMap<String, List<TreeStruct>>();
		for (TreeStruct cert : certs) {
			List<TreeStruct> col = pids.get(cert.getPid());
			if (col == null) {
				col = new ArrayList<TreeStruct>();
				pids.put(cert.getPid(), col);
			}
			col.add(cert);
		}

		TreeStruct c = pids.get("0").get(0);
		Document root = new Document();
		root.put(c.getName(), c.getValue());
		Tree.findChild(root, pids.get(c.getId()), pids);
		System.out.println(root.toJson());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMP imp = new IMP();
		imp.impCertification_TZ("d://test//TZ.csv");

	}

}
