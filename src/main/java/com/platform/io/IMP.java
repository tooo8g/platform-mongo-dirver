package com.platform.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.platform.io.bean.Certification;
import com.platform.io.bean.Price;
import com.platform.io.bean.Product;
import com.platform.io.bean.ProductInfo;
import com.platform.io.bean.PurchaseBidding;
import com.platform.io.bean.Standardization;
import com.platform.io.bean.TreeStruct;
import com.platform.mongo.s1.MongoDirverS1;
import com.platform.mongo.util.Tree;

public class IMP {
	
	/**
	 * 供应商菜单录入
	 * 
	 * @param fileName
	 */
	public void impSuppliers_menu_tzs(String fileName) {
		List<TreeStruct> certs = MSIO.readSuppliers(fileName);
		Map<String, List<TreeStruct>> pids = new HashMap<String, List<TreeStruct>>();
		Document root = new Document();
		for (TreeStruct cert : certs) {
			if(cert.getId().equals("0")){
				root.put(cert.getName(), cert.getValue());
			}
			List<TreeStruct> col = pids.get(cert.getPid());
			if (col == null) {
				col = new ArrayList<TreeStruct>();
				pids.put(cert.getPid(), col);
			}
			col.add(cert);
		}

		List<TreeStruct> list = pids.get("0");
		Tree.findChild(root, list, pids, false);
		MongoDirverS1 md = new MongoDirverS1();
		md.addSuppliers_menu_tz(root);
		System.out.println(root.toJson());
		md.close();
	}

	/**
	 * 认证
	 * 
	 * @param fileName
	 */
	public void impStandard(String fileName) {
		List<Standardization> stands = MSIO.readStandardizations(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (Standardization stand : stands) {
			md.addLatestStandards(stand);
		}
		md.close();
	}

	/**
	 * 资质
	 * 
	 * @param fileName
	 */
	public void impCertification(String fileName) {
		List<Certification> certs = MSIO.readCertification(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (Certification cert : certs) {
			md.addCertification(cert);
		}
		md.close();
	}

	
	
	/**
	 * 招标信息
	 * 
	 * @param fileName
	 */
	public void impPurchaseBidding(String fileName) {
		List<PurchaseBidding> purchaseBiddings = CSVIO.readPurchaseBidding(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (PurchaseBidding pb : purchaseBiddings) {
			md.addPurchaseBidding(pb);
		}
		md.close();
	}

	/**
	 * 价格
	 * 
	 * @param fileName
	 */
	public void impPrice(String fileName) {
		List<Price> prices = CSVIO.readPrice(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (Price sp : prices) {
			md.addPrice(sp);
		}
		md.close();
	}

	/**
	 * 资质-铁路总公司铁路专用产品认证采信菜单
	 * 
	 * @param fileName
	 */
	public void impCertification_menu_tz(String fileName) {
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
		MongoDirverS1 md = new MongoDirverS1();
		md.addCertification_menu_tz(root);
		md.close();
	}

	/**
	 * 增加企业名称
	 * 
	 * @param fileName
	 */
	public void impCompany_name(String fileName) {
		List<Certification> list = MSIO.readCompany_name(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (int i = 0; i < list.size(); i++) {
			md.addCompany_name(list.get(i));
		}

		md.close();
	}

	/**
	 * 增加产品
	 * 
	 * @param fileName
	 */
	public void impProduct(String fileName) {
		List<Product> list = MSIO.readProduct(fileName);		
		MongoDirverS1 md = new MongoDirverS1();
		for (int i = 0; i < list.size(); i++) {
			md.addProduct(list.get(i));
		}

		md.close();
	}

	/**
	 * 产品标识代码
	 * 
	 * @param fileName
	 * @author zhangyb
	 */
	public void impProductInfo(String fileName) {
		List<ProductInfo> list = CSVIO.readProductInfo(fileName);
		MongoDirverS1 md = new MongoDirverS1();
		for (int i = 0; i < list.size(); i++) {
			md.addProductInfo(list.get(i));
		}

		md.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IMP imp = new IMP();
//		imp.impProductInfo("d://test//productInfo.csv");
		imp.impPurchaseBidding("E://test//123123.csv");
	}
}