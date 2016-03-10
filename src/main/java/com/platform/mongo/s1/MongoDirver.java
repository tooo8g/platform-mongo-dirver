package com.platform.mongo.s1;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.platform.io.bean.Certification;
import com.platform.io.bean.Certification_Detail;
import com.platform.io.bean.Code;
import com.platform.io.bean.OrderOrContract;
import com.platform.io.bean.Price;
import com.platform.io.bean.Product;
import com.platform.io.bean.ProductInfo;
import com.platform.io.bean.PurchaseBidding;
import com.platform.io.bean.Purchasing;
import com.platform.io.bean.Standardization;
import com.platform.io.bean.Supply;
import com.platform.mongo.s1.dao.MongoDao;
import com.platform.mongo.util.TimeUtil;

public class MongoDirver {

	private MongoDao client;

	public MongoDirver() {
		client = new MongoDao();
	}

	public void close() {
		client.close();
	}

	public static void main(String[] args) {
		MongoDirver md = new MongoDirver();
		// md.test2();
		// md.test3();
		// md.test4();
		// md.test();
		// md.addCP("中厚钢板", "AS-2", "15", "企业A");
		// md.addCP("中厚钢板", "AS-2", "16", "企业A");
		// md.addCP("中厚钢板", "BS-3", "150", "企业B");
		// md.addCP("中厚钢板", "BS-4", "155", "企业B");
		// md.addCP("中厚钢板", "AS-2", "16", "企业B");
		// md.addCP("中厚钢板", "AS-3", "15", "企业C");
		// md.test5();
		// String json = md.findCP_tree(2);
		// System.out.println(md.queryCPMenu(2));
		// System.out.println(md.queryNextCPMenu(3, "11"));
		// md.queryGroupList("test", "cp_detail", new Document("group", "111"),
		// null);
		// System.out.println(md.queryProductDetail("5620ba5d772fb525e42f2c11"));
		// System.out.println(md.queryGYSCPMenu("561e18cbdfae591f8ceae127"));
		// System.out.println(md.queryGYSCPDetail("561e18cbdfae591f8ceae127",
		// "111", 0, 10));
		// md.addSupplierWarning(
		// "企业A",
		// "72551051-9",
		// "销售授权有效期弄虚作假2",
		// "《中国铁路总公司关于公布2015年6月份供应商信用评价结果的通知2》"
		// +
		// "（铁总物资函〔2015〕788号）。自本通知发布之日起，暂停与绵阳市铁人电气有限公司合作关系12个月，并纳入供应商年度信用评价。暂停期限内，"
		// +
		// "铁路各单位不得接受其参与铁路物资设备招标及其他采购活动；对其已中标但尚未发放中标通知书的，取消中标资格；对已发放中标通知书或已签订合同的，"
		// + "要加强质量检验和监控。在合同履行过程中，存在违约行为的追究其违约责任。", "中国铁路总公司",
		// new Date(), new Date(), "");
		// System.out.println(md.querySupplierWarning("2015〕", 0, 2));
		// md.addRailWarning("河北辛集腾跃实业有限公司", "货车高摩擦系A统合成闸瓦", "不合B格",
		// "自动摩C擦磨耗性能", "2015年第4批",
		// "");
		// System.out.println(md.queryRailWarning("A", 0, 10));
		try {
			// File f = new File("D://test//1.jpg");
			// byte[] data = new byte[(int) f.length()];
			// FileInputStream fin = new FileInputStream(f);
			// fin.read(data);
			// fin.close();
			// md.saveFile("test", "img", "1", data);
			// System.out.println("save image");
			// md.writeFile("test", "img", "1", "D://test//2.jpg");
			// System.out.println(md.queryLatestStandards("", 0, 5));
			// System.out.println(md.queryStandards(null, null, null, null,
			// null,
			// 0, 10));
			// System.out.println(md.queryCertifications("250", 0, 10));
			// System.out.println(md.querySteelPrice("盘螺", null, null, null,
			// null,
			// 0, 10));
			// System.out.println(md.queryCertification_menu_tz());
			// System.out.println(md
			// .queryPrice("普通硅酸盐水泥", null, null, null, 0, 20));
			// System.out.println(md.queryCompanyForPrice("高线", "Φ6.5"));
			// md.queryPriceHistory("56a1d82e4d462a2b1476acfc");
			String s = md.queryPurchaseBidding("", 0, 3);
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		md.close();
	}

	/**
	 * 增加产品信息
	 * 
	 * @param cpmc
	 *            产品名称
	 * @param ggxh
	 *            规格型号
	 * @param jg
	 *            价格
	 * @param qymc
	 *            企业名称
	 */
	public void addProduct(String cpmc, String ggxh, String jg, String qymc) {
		// 有此产品
		String id = client.queryOne("test", "cp_tree3", eq("zwmc", cpmc),
				"_id", String.class);
		if (id != null) {
			// 有此规格型号
			String zx_id = client.queryOne("test", "cp_tree3",
					and(eq("_id", id), eq("zx.zwmc", ggxh)), "_id",
					String.class);
			if (zx_id == null) {
				// 增加规格型号
				Document value = new Document();
				value.put("zwmc", ggxh);
				client.appendOne("test", "cp_tree3", eq("_id", id), "zx", value);
			}
			// 有此供应商
			ObjectId gys_id = client.queryOne("test", "gys", eq("qymc", qymc),
					"_id", ObjectId.class);
			ObjectId cp_detail_id = new ObjectId();
			Document gys_cp_data = new Document();
			gys_cp_data.put("cp_detail_id", cp_detail_id);
			if (gys_id == null) {
				gys_id = new ObjectId();
				// 增加供应商
				List<Document> gys_cp_datas = new ArrayList<Document>();
				gys_cp_datas.add(gys_cp_data);
				Document gys_data = new Document("_id", gys_id)
						.append("qymc", qymc).append("add_time", new Date())
						.append("cp", gys_cp_datas);
				client.addOne("test", "gys", gys_data);
			} else {
				// 增加供应商产品列表
				client.appendOne("test", "gys", eq("_id", gys_id), "cp",
						gys_cp_data);
			}
			// 将原产品注销
			client.updateField(
					"test",
					"cp_detail",
					and(eq("group", id), eq("ggxh", ggxh),
							eq("gys_id", gys_id), eq("valid", "1")), "valid",
					"0");
			// 增加产品
			Document cp_data = new Document("_id", cp_detail_id)
					.append("group", id).append("cpmc", cpmc)
					.append("ggxh", ggxh).append("jg", jg)
					.append("gys_id", gys_id).append("add_time", new Date())
					.append("valid", "1");
			client.addOne("test", "cp_detail", cp_data);
		}
	}

	/**
	 * 获取菜单
	 * 
	 * @param level
	 *            菜单级别(1-3)
	 * @return
	 */
	public String queryProductMenu(int level) {
		List<Document> cp_data = client.queryList("test", "cp_tree" + level,
				null, new BasicDBObject("_id", 0).append("zx", 0)).into(
				new ArrayList<Document>());
		Document data = new Document("data", cp_data);
		return data.toJson();
	}

	/**
	 * 获取子菜单
	 * 
	 * @param level
	 *            菜单级别(1-3)
	 * @param id
	 *            父级菜单ID
	 * @return
	 */
	public String queryNextProductMenu(int level, String id) {
		Document parent_data = client.queryOne("test", "cp_tree" + (level - 1),
				eq("_id", id), "zx");
		@SuppressWarnings("unchecked")
		List<Document> parent_ids = parent_data.get("zx", ArrayList.class);
		List<Document> child_data = client.queryListByArray("test",
				"cp_tree" + level, "_id", parent_ids, "id", String.class,
				new BasicDBObject("_id", 0).append("zx", 0)).into(
				new ArrayList<Document>());
		Document data = new Document("data", child_data);
		return data.toJson();
	}

	/**
	 * 获取菜单下产品列表
	 * 
	 * @param id
	 * @return
	 */
	public String queryProductList(String id, int skip, int limit) {
		Document tree3 = client.queryOne("test", "cp_tree3", eq("_id", id),
				null);
		Bson filters = and(eq("group", id), eq("valid", "1"));
		int count = client.queryCount("test", "cp_detail", filters);
		List<Document> cp_detail = client.queryList("test", "cp_detail",
				filters, new BasicDBObject("valid", 0), skip, limit).into(
				new ArrayList<Document>());
		for (Document cp : cp_detail) {
			ObjectId gys_id = cp.getObjectId("gys_id");
			Document gys = client.querySingle("test", "gys", eq("_id", gys_id),
					new BasicDBObject("cp", 0));
			cp.put("gys_id", gys);
		}
		tree3.append("count", count);
		tree3.put("cp", cp_detail);
		return tree3.toJson();
	}

	/**
	 * 获取产品详情
	 * 
	 * @param id
	 * @return
	 */
	public String queryProductDetail(String id) {
		Bson filters = eq("_id", new ObjectId(id));
		Document cp = client.querySingle("test", "cp_detail", filters,
				new BasicDBObject("group", 0).append("valid", 0));
		ObjectId gys_id = (ObjectId) cp.remove("gys_id");
		filters = eq("_id", gys_id);
		Document gys = client.querySingle("test", "gys", filters,
				new BasicDBObject("cp", 0));
		cp.put("gys", gys.getString("qymc"));
		return cp.toJson();
	}

	/**
	 * 获取供应商产品树
	 * 
	 * @param id
	 * @return
	 */
	public String querySupplierProductMenu(String id) {
		Document gys = client.querySingle("test", "gys",
				eq("_id", new ObjectId(id)), new BasicDBObject("cp", 1));
		List<Document> cps = gys.get("cp", ArrayList.class);
		List<ObjectId> cp_detail_ids = new ArrayList<ObjectId>();
		for (Document cp : cps)
			cp_detail_ids.add(cp.getObjectId("cp_detail_id"));
		Bson filters = in("_id", cp_detail_ids);
		List<Document> tree3s = client.queryDistincValue("test", "cp_detail",
				filters, "$group");
		// 逐个查找上级目录
		Document tree = new Document();
		tree.put("id", id);
		for (Document tree3_ids : tree3s) {
			String tree3_id = tree3_ids.getString("_id");
			Document tree3 = client.querySingle("test", "cp_tree3",
					eq("_id", tree3_id), new BasicDBObject("zwmc", 1));
			Document tree2 = client.querySingle("test", "cp_tree2",
					eq("zx.id", tree3_id), new BasicDBObject("zwmc", 1));
			String tree2_id = tree2.getString("_id");
			Document tree1 = client.querySingle("test", "cp_tree1",
					eq("zx.id", tree2_id), new BasicDBObject("zwmc", 1));

			String tree3_zwmc = tree3.getString("zwmc");
			String tree2_zwmc = tree2.getString("zwmc");
			String tree1_zwmc = tree1.getString("zwmc");
			Document d1 = tree.get(tree1_zwmc, Document.class);
			if (d1 == null) {
				Document d2 = new Document();
				d2.put(tree3_zwmc, tree3);
				d1 = new Document();
				d1.put(tree2_zwmc, d2);
				tree.put(tree1_zwmc, d1);
			} else {
				Document d2 = d1.get(tree2_zwmc, Document.class);
				if (d2 == null) {
					d2 = new Document();
					d2.put(tree3_zwmc, tree3);
					d1.put(tree2_zwmc, d2);
				} else {
					d2.put(tree3_zwmc, tree3);
				}
			}
		}
		return tree.toJson();
	}

	/**
	 * 供应商产品明细
	 * 
	 * @param id
	 * @param group
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String querySupplierProductDetail(String id, String group, int skip,
			int limit) {
		Bson filters = and(eq("group", group), eq("gys_id", new ObjectId(id)),
				eq("valid", "1"));
		int count = client.queryCount("test", "cp_detail", filters);
		List<Document> cp_detail = client.queryList("test", "cp_detail",
				filters, new BasicDBObject("valid", 0), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.append("count", count);
		data.put("cp", cp_detail);
		return data.toJson();
	}

	/**
	 * 新增供应商处理通报
	 * 
	 * @param gysmc
	 *            供应商名称
	 * @param zzjgdm
	 *            组织机构代码
	 * @param blxw
	 *            不良行为
	 * @param cljd
	 *            处理决定
	 * @param cljg
	 *            处理机关
	 * @param start_time
	 *            开始时间
	 * @param end_time
	 *            结束时间
	 * @param bz
	 *            备注
	 */
	public void addSupplierWarning(String gysmc, String zzjgdm, String blxw,
			String cljd, String cljg, Date kssj, Date jssj, String bz) {
		Bson filters = or(eq("qymc", gysmc), eq("zzjgdm", zzjgdm));
		ObjectId gys_id = client.queryOne("test", "gys", filters, "_id",
				ObjectId.class);
		ObjectId cltb_id = new ObjectId();
		Document cltb = new Document();
		cltb.put("_id", cltb_id);
		cltb.put("qymc", gysmc);
		cltb.put("zzjgdm", zzjgdm);
		cltb.put("blxw", blxw);
		cltb.put("cljd", cljd);
		cltb.put("cljg", cljg);
		cltb.put("start_time", kssj);
		cltb.put("end_time", jssj);
		cltb.put("bz", bz);
		cltb.put("add_time", new Date());
		// 添加供应商处理通报
		client.addOne("test", "cltb", cltb);
		List<Document> gyscltb = new ArrayList<Document>();
		Document cltb_ids = new Document();
		cltb_ids.put("cltb_id", cltb_id);
		gyscltb.add(cltb_ids);
		// 有此供应商
		if (gys_id != null) {
			Bson append_filters = eq("_id", gys_id);
			client.appendOne("test", "gys", append_filters, "gyscltb", cltb_ids);
		} else {
			Document data = new Document("qymc", gysmc)
					.append("zzjgdm", zzjgdm).append("gyscltb", gyscltb)
					.append("add_time", new Date());
			client.addOne("test", "gys", data);
		}
	}

	/**
	 * 查询供应商处理通报
	 * 
	 * @param str
	 *            关键字
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String querySupplierWarning(String str, int skip, int limit) {
		Bson filters = or(regex("qymc", "^.*" + str + ".*$"),
				regex("blxw", "^.*" + str + ".*$"),
				regex("cljd", "^.*" + str + ".*$"));
		int count = client.queryCount("test", "cltb", filters);
		List<Document> cltb = client.queryList("test", "cltb", filters,
				new BasicDBObject("_id", 0).append("add_time", 0),
				new BasicDBObject("add_time", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("cltb", cltb);
		return data.toJson();
	}

	/**
	 * 增加铁道质量监督抽查通报
	 * 
	 * @param qymc
	 *            企业名称
	 * @param cpmc
	 *            产品名称
	 * @param jyjg
	 *            检验结果
	 * @param bhgxm
	 *            不合格项目
	 * @param pc
	 *            批次
	 * @param bz
	 *            备注
	 */
	public void addRailWarning(String qymc, String cpmc, String jyjg,
			String bhgxm, String pc, String bz) {
		Bson filters = eq("qymc", qymc);
		ObjectId gys_id = client.queryOne("test", "gys", filters, "_id",
				ObjectId.class);
		ObjectId rail_id = new ObjectId();
		Document rail = new Document();
		rail.put("_id", rail_id);
		rail.put("qymc", qymc);
		rail.put("cpmc", cpmc);
		rail.put("jyjg", jyjg);
		rail.put("bhgxm", bhgxm);
		rail.put("pc", pc);
		rail.put("bz", bz);
		rail.put("add_time", new Date());
		// 添加铁道质量通报
		client.addOne("test", "tdcltb", rail);
		List<Document> tdcltb = new ArrayList<Document>();
		Document rail_ids = new Document();
		rail_ids.put("cltb_id", rail_id);
		tdcltb.add(rail_ids);
		// 有次供应商
		if (gys_id != null) {
			Bson append_filters = eq("_id", gys_id);
			client.appendOne("test", "gys", append_filters, "tdcltb", rail_ids);
		} else {
			Document data = new Document("qymc", qymc).append("tdcltb", tdcltb)
					.append("add_time", new Date());
			client.addOne("test", "gys", data);
		}
	}

	/**
	 * 查询铁道质量监督抽查通报
	 * 
	 * @param str
	 *            关键字
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryRailWarning(String str, int skip, int limit) {
		Bson filters = or(regex("qymc", "^.*" + str + ".*$"),
				regex("cpmc", "^.*" + str + ".*$"),
				regex("bhgxm", "^.*" + str + ".*$"),
				regex("pc", "^.*" + str + ".*$"));
		int count = client.queryCount("test", "tdcltb", filters);
		List<Document> tdcltb = client.queryList("test", "tdcltb", filters,
				new BasicDBObject("_id", 0).append("add_time", 0),
				new BasicDBObject("add_time", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("tdcltb", tdcltb);
		return data.toJson();
	}

	/**
	 * 增加招标采购信息
	 * 
	 * @author niyn
	 * @param cgbh
	 *            采购编号
	 * @param cgmc
	 *            采购名称
	 * @param zzdw
	 *            组织单位
	 * @param gglx
	 *            公告类型
	 * @param cgpz
	 *            采购品种
	 * @param cgdq
	 *            采购地区
	 * @param fbsj
	 *            发布时间
	 * @param sjly
	 *            数据来源
	 * 
	 */
	public void addPurchaseBidding(PurchaseBidding pb) {
		Document doc = new Document();
		doc.put("purchaseOrderNo", pb.getPurchaseOrderNo());
		doc.put("purchaserName", pb.getPurchaserName());
		doc.put("purchaserCompany", pb.getPurchaserCompany());
		doc.put("announcementType", pb.getAnnouncementType());
		doc.put("purchaserVariety", pb.getPurchaserVariety());
		doc.put("purchaserArea", pb.getPurchaserArea());
		doc.put("purchaserFileGetTime",
				TimeUtil.parserTime(pb.getPurchaserFileGetTime()));
		doc.put("dataSource", pb.getDataSource());
		doc.put("publishTime", TimeUtil.parserTime(pb.getPublishTime()));
		doc.put("add_time", new Date());
		doc.put("edit_time", TimeUtil.parserTime(pb.getEditTime()));
		client.addOne("test", "purchase_bidding", doc);
	}

	/**
	 * 查询招标采购信息
	 * 
	 * @author niyn
	 * @param str
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryPurchaseBidding(String str, int skip, int limit) {
		Bson filters = null;
		if (str != null && !str.equals(""))
			filters = or(regex("purchaseOrderNo", "^.*" + str + ".*$"),
					regex("purchaserName", "^.*" + str + ".*$"),
					regex("purchaserCompany", "^.*" + str + ".*$"),
					regex("purchaserVariety", "^.*" + str + ".*$"),
					regex("purchaserArea", "^.*" + str + ".*$"),
					regex("announcementType", "^.*" + str + ".*$"),
					regex("purchaserFileGetTime", "^.*" + str + ".*$"),
					regex("publishTime", "^.*" + str + ".*$"),
					regex("dataSource", "^.*" + str + ".*$"));

		int count = client.queryCount("test", "purchase_bidding", filters);
		List<Document> zbcg = client.queryList("test", "purchase_bidding",
				filters, new BasicDBObject("_id", 0),
				new BasicDBObject("publishTime", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx", zbcg);
		return data.toJson();
	}

	/**
	 * 增加标准信息
	 * 
	 * @param standard
	 */
	public void addLatestStandards(Standardization standard) {
		Document ls = new Document();
		ls.put("standard_group", standard.getStandard_group());
		ls.put("standard_id", standard.getStandard_id());
		ls.put("standard_name", standard.getStandard_name());
		ls.put("replace_id", standard.getReplace_id());
		ls.append("publish_date",
				TimeUtil.parserTime(standard.getPublish_date()));
		ls.put("execute_date", TimeUtil.parserTime(standard.getExecute_date()));
		ls.put("standard_status", standard.getStandard_status());
		ls.put("special_subject", standard.getSpecial_subject());
		ls.put("add_time", new Date());
		ObjectId _id = client.queryOne("test", "standardization",
				eq("standard_id", standard.getStandard_id()), "_id",
				ObjectId.class);
		// 如果标准编号存在，则这将这条历史记录更新。不存在则新增。
		if (_id != null) {
			client.updateOne("test", "standardization", eq("_id", _id), ls);
		} else {
			client.addOne("test", "standardization", ls);
		}
	}

	/**
	 * 最新发布标准信息查询
	 * 
	 * @param str
	 *            关键字
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryLatestStandards(String str, int skip, int limit) {
		Bson filters = null;
		if (str != null && !str.equals(""))
			filters = or(regex("standard_group", "^.*" + str + ".*$"),
					regex("standard_id", "^.*" + str + ".*$"),
					regex("standard_name", "^.*" + str + ".*$"),
					regex("replace_id", "^.*" + str + ".*$"));
		int count = client.queryCount("test", "standardization", filters);
		List<Document> bzxx = client.queryList("test", "standardization",
				filters, new BasicDBObject("_id", 0),
				new BasicDBObject("publish_date", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx", bzxx);
		return data.toJson();
	}

	/**
	 * 技术标准查询
	 * 
	 * @param standard_group
	 *            标准类型
	 * @param str
	 *            标准编号、标准名称
	 * @param standard_status
	 *            文件状态
	 * @param special_subject
	 *            专业分类
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryStandards(String str, String standard_group,
			String standard_status, String special_subject, int skip, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (standard_group != null && !standard_group.equals(""))
			condition.add(eq("standard_group", standard_group));
		if (str != null && !str.equals(""))
			condition.add(or(regex("standard_name", "^.*" + str + ".*$"),
					regex("standard_id", "^.*" + str + ".*$")));
		if (standard_status != null && !standard_status.equals(""))
			condition.add(eq("standard_status", standard_status));
		if (special_subject != null && !special_subject.equals(""))
			condition.add(eq("special_subject", special_subject));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "standardization", filters);
		List<Document> bzxx = client.queryList("test", "standardization",
				filters, new BasicDBObject("_id", 0),
				new BasicDBObject("add_time", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx", bzxx);
		return data.toJson();
	}

	/**
	 * 查询资质信息
	 * 
	 * @param str
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryCertifications(String str, int skip, int limit) {
		System.out.println(str);
		Bson filters = null;
		if (str != null && !str.equals(""))
			filters = or(regex("product_range", "^.*" + str + ".*$"),
					regex("cert_name", "^.*" + str + ".*$"));
		int count = client.queryCount("test", "certification", filters);
		List<Document> rzxx = client.queryList("test", "certification",
				filters, null, new BasicDBObject("publish_date", -1), skip,
				limit).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx", rzxx);
		System.out.println(data.toJson());
		return data.toJson();
	}

	/**
	 * 增加铁路总公司铁路专用产品认证采信树形菜单
	 * 
	 * @param data
	 */
	public void addCertification_menu_tz(Document data) {
		client.addOne("test", "certification_menu_tz", data);
	}

	/**
	 * 增加供应商树形菜单
	 * 
	 * @param data
	 */
	public void addSuppliers_menu_tz(Document data) {
		client.addOne("test", "suppliers_menu_tz", data);
	}

	/**
	 * 查询铁路总公司铁路专用产品认证采信树形菜单
	 * 
	 * @return
	 */
	public String queryCertification_menu_tz() {
		Document result = client.querySingle("test", "certification_menu_tz",
				null, new BasicDBObject("_id", 0));
		if (result != null)
			return result.toJson();
		return "";
	}

	/**
	 * 增加资质信息
	 * 
	 * @param cert
	 */
	public void addCertification(Certification cert) {
		Document d = new Document();
		d.put("product_range", cert.getProduct_range());
		d.put("company_name", cert.getCompany_name());
		d.put("cert_unit", cert.getCert_unit());
		d.put("cert_name", cert.getCert_name());
		d.put("cert_num", cert.getCert_num());
		d.put("issue_organization", cert.getIssue_organization());
		d.put("cert_standards", cert.getCert_standards());
		d.put("publish_date", TimeUtil.parserTime(cert.getPublish_date()));
		d.put("valid_date", TimeUtil.parserTime(cert.getValid_date()));
		d.put("cert_status", cert.getCert_status());
		d.put("cert_status_id", cert.getCert_status_id());
		d.put("mount_code", cert.getMount_code());
		d.put("project_code", cert.getProject_code());
		d.put("organization_code", cert.getOrganization_code());
		d.put("reg_addr", cert.getReg_addr());
		d.put("post_code", cert.getPost_code());
		d.put("product_kind", cert.getProduct_kind());
		d.put("product_addr", cert.getProduct_addr());
		d.put("notification_number", cert.getNotification_number());
		d.put("cert_condition", cert.getCert_condition());
		d.put("cert_expand", cert.getCert_expand());
		d.put("remark", cert.getRemark());
		List<Certification_Detail> details = cert.getCert_detail();
		List<Document> data = new ArrayList<Document>();
		for (Certification_Detail cd : details) {
			Document doc = new Document();
			doc.put("product_code", cd.getProduct_code());
			doc.put("specification", cd.getSpecification());
			doc.put("specification_status", cd.getSpecification_status());
			doc.put("product_property_name", cd.getProduct_property_name());
			doc.put("expand", cd.getExpand());
			data.add(doc);
		}
		d.put("cert_detail", data);
		d.put("add_time", new Date());
		client.addOne("test", "certification", d);
	}

	/**
	 * 增加企业名称
	 * 
	 * @param cert
	 */
	public void addCompany_name(Certification cert) {
		Document d = new Document();
		d.put("company_name", cert.getCompany_name());
		client.addOne("test", "company", d);
	}

	/**
	 * 增加价格
	 * 
	 * @param price
	 */
	public void addPrice(Price price) {
		// 按照 品名+规格型号+厂家+供应地区 的唯一性查询是否已有记录
		Bson filters = and(eq("name", price.getName()),
				eq("specification", price.getSpecification()),
				eq("company", price.getCompany()), eq("city", price.getCity()));
		ObjectId _id = client.queryOne("test", "price", filters, "_id",
				ObjectId.class);
		if (_id != null) {
			Document history = new Document();
			history.put("p_id", _id);
			history.put("price", price.getPrice());
			history.put("date", TimeUtil.parserTime(price.getDate()));
			// 修改更新时间、价格涨幅
			Document d = client.querySingle("test", "price_history",
					new BasicDBObject("p_id", _id), new BasicDBObject("price",
							1), new BasicDBObject("date", -1));
			// 历史数据中最新的一条价格记录
			int price_history = d.getInteger("price", 0);
			int price_range = price.getPrice() - price_history;
			Document newData = new Document("update_time",
					TimeUtil.parserTime(price.getDate())).append("price_range",
					price_range).append("price", price.getPrice());
			client.updateOne("test", "price", eq("_id", _id), newData);
			// 增加一条价格详情
			client.addOne("test", "price_history", history);
		} else {
			Document d = new Document();
			_id = new ObjectId();
			d.put("_id", _id);
			d.put("name", price.getName());
			d.put("specification", price.getSpecification());
			d.put("texture", price.getTexture());
			d.put("company", price.getCompany());
			d.put("area", price.getArea());
			d.put("city", price.getCity());
			d.put("price", price.getPrice());
			d.put("priceType", price.getPriceType());
			d.put("price_range", 0);
			d.put("expand", price.getExpand());
			d.put("add_time", new Date());
			d.put("update_time", TimeUtil.parserTime(price.getDate()));
			// 增加一条主记录
			client.addOne("test", "price", d);
			// 增加一条价格详情
			Document history = new Document();
			history.put("p_id", _id);
			history.put("price", price.getPrice());
			history.put("date", TimeUtil.parserTime(price.getDate()));
			client.addOne("test", "price_history", history);
		}
	}

	/**
	 * 查询价格
	 * 
	 * @param name
	 *            物资名称
	 * @param date
	 *            价格日期
	 * @param specification
	 *            规格型号
	 * @param city
	 *            城市
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryPrice(String name, String date, String specification,
			String city, int skip, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (name != null && !name.equals(""))
			condition.add(eq("name", name));
		if (date != null && !date.equals(""))
			condition.add(eq("update_time", TimeUtil.parserTime(date)));
		if (specification != null && !specification.equals(""))
			condition.add(eq("specification", specification));
		if (city != null && !city.equals(""))
			condition.add(eq("city", city));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "price", filters);
		List<Document> jgxx = client.queryList("test", "price", filters, null,
				new BasicDBObject("update_time", -1), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("jgxx", jgxx);
		return data.toJson();
	}

	/**
	 * 查询价格
	 * 
	 * @param id
	 *            价格ID
	 * @return
	 */
	public Document queryPrice(String id) {
		return client.querySingle("test", "price", eq("_id", new ObjectId(id)),
				new BasicDBObject("_id", 0));
	}

	/**
	 * 查询历史价格
	 * 
	 * @param id
	 * @return
	 */
	public List<Document> queryPriceHistory(String id) {
		ObjectId _id = new ObjectId(id);
		Date now = TimeUtil.getToday().getTime();
		Date month_ago = TimeUtil.getDay(Calendar.MONTH, -1).getTime();
		// 得到一个月前的最新的价格数据
		Document d1 = client.querySingle("test", "price_history",
				and(eq("p_id", _id), lte("date", month_ago)),
				new BasicDBObject("price", 1).append("date", 1),
				new BasicDBObject("date", -1));
		if (d1 == null)
			d1 = new Document("price", 0);
		// 得到近一个月的价格数据
		List<Document> d2 = client.queryList(
				"test",
				"price_history",
				and(eq("p_id", new ObjectId(id)), gte("date", month_ago),
						lte("date", now)), new BasicDBObject("_id", 0)).into(
				new ArrayList<Document>());
		d2.add(0, d1);
		return d2;
	}

	/**
	 * 查询历史价格
	 * 
	 * @param name
	 *            物资名称
	 * @param specification
	 *            规格型号
	 * @param company
	 *            厂家
	 * @param city
	 *            城市
	 * @return
	 */
	public List<Document> queryPriceHistory(String name, String specification,
			String company, String city) {
		Date now = TimeUtil.getToday().getTime();
		Date month_ago = TimeUtil.getDay(Calendar.MONTH, -1).getTime();
		// 得到价格主ID
		ObjectId _id = client.queryOne(
				"test",
				"price",
				and(eq("name", name), eq("specification", specification),
						eq("company", company), eq("city", city)), "_id",
				ObjectId.class);
		if (_id == null) {
			Document d = new Document();
			d.put("price", 0);
			List<Document> result = new ArrayList<Document>();
			result.add(d);
			return result;
		}
		// 得到一个月前的最新的价格数据
		Document d1 = client.querySingle("test", "price_history",
				and(eq("p_id", _id), lte("date", month_ago)),
				new BasicDBObject("price", 1).append("date", 1),
				new BasicDBObject("date", -1));
		if (d1 == null)
			d1 = new Document("price", 0);
		// 得到近一个月的价格数据
		List<Document> d2 = client.queryList("test", "price_history",
				and(eq("p_id", _id), gte("date", month_ago), lte("date", now)),
				new BasicDBObject("_id", 0)).into(new ArrayList<Document>());
		d2.add(0, d1);
		return d2;
	}

	/**
	 * 查询厂家/产地
	 * 
	 * @param name
	 *            物资名称
	 * @param specification
	 *            规格型号
	 * @return
	 */
	public String queryCompanyForPrice(String name, String specification) {
		List<Document> d = client.queryDistincValue("test", "price",
				and(eq("name", name), eq("specification", specification)),
				"$company");
		List<String> companys = new ArrayList<String>();
		for (Document c : d) {
			System.out.println(c.toJson());
			companys.add(c.getString("_id"));
		}
		Document data = new Document();
		data.put("companys", companys);
		return data.toJson();
	}

	private String findCPMenu(int deep) {
		if (deep == 1) {
			List<Document> tree1 = client.queryList("test", "cp_tree1", null,
					new BasicDBObject("_id", 0).append("zx", 0)).into(
					new ArrayList<Document>());
			Document data = new Document();
			data.append("tree", tree1);
			return data.toJson();
		} else if (deep == 2) {
			List<Document> tree1s = client.queryList("test", "cp_tree1", null,
					new BasicDBObject("_id", 0))
					.into(new ArrayList<Document>());
			for (Document tree1 : tree1s) {
				@SuppressWarnings("unchecked")
				List<Document> tree2s = tree1.get("zx", ArrayList.class);
				List<Document> tree2 = client.queryListByArray("test",
						"cp_tree2", "_id", tree2s, "id", String.class,
						new BasicDBObject("zx", 0).append("_id", 0)).into(
						new ArrayList<Document>());
				tree1.put("zx", tree2);
			}
			Document data = new Document();
			data.append("tree", tree1s);
			return data.toJson();
		} else if (deep == 3) {

		} else if (deep == 4) {

		}

		return null;
	}

	public String queryBysTandard(String db, String table, String cert_standard) {
		// TODO Auto-generated method stub
		// Bson filters = in("_id", cp_detail_ids);
		// List<Document> tree3s = client.queryDistincValue("test", "cp_detail",
		// filters, "$group");
		// BasicDBObject queryObject =new BasicDBObject().append(
		// "standard_name", cert_standard);
		Bson filters = eq("standard_id", cert_standard);

		// List<Document> standard_names = client.queryBysTandard(db, table,
		// filters,"$group");
		List<Document> standard_names = client.queryList(db, table, filters,
				new BasicDBObject()).into(new ArrayList<Document>());

		Document data = new Document();
		data.put("standard_names", standard_names);
		return data.toJson();
	}

	public String queryByNum(String db, String table, String cert_num) {
		Bson filters = eq("cert_num", cert_num);
		// TODO Auto-generated method stub
		List<Document> standard_names = client.queryList(db, table, filters,
				new BasicDBObject()).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("standard_names", standard_names);
		return data.toJson();
	}

	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		// 判断供应商是否存在
		String company = product.getCompany();
		ObjectId id = client.queryOne("test", "company",
				eq("company_name", company), "_id", ObjectId.class);
		ObjectId company_id_new = new ObjectId();
		if (id == null) {// 如果供应商不存在 则保存此供应商到数据库
			Document d = new Document();
			d.put("_id", company_id_new);
			d.put("company_name", company);
			client.addOne("test", "company", d);
		}
		// 判断产品是否存在
		String product_name = product.getProduct_name();// 获得产品名称
		String specification = product.getSpecification();// 获取产品规格型号

		Bson specification_filter = eq("specification", specification);
		Bson product_name_filter = eq("product_name", product_name);
		ObjectId product_id = client.queryOne("test", "product",
				and(specification_filter, product_name_filter), "_id",
				ObjectId.class);
		// ObjectId product_id =null;
		// 如果数据库中不存在 插入数据
		List<Document> certifications = new ArrayList<Document>();
		ObjectId product_id_new = new ObjectId();
		if (product_id == null) {
			// 首先查询出资质
			String cert_num = product.getCert_num();
			Bson filters = eq("cert_num", cert_num);
			certifications = client.queryList("test", "certification", filters,
					new BasicDBObject()).into(new ArrayList<Document>());
			// 根据执行标准编号查询出标准名称
			List<String> standard_names = new ArrayList<String>();
			String cert_standards = product.getCert_standards();
			String[] strs = cert_standards.split("；");
			for (int j = 0; j < strs.length; j++) {
				String cert_standard = strs[j];
				Bson cert_standard_filters = eq("standard_id", cert_standard);
				String standard_name = client.queryOne("test",
						"standardization", cert_standard_filters,
						"standard_name", String.class);
				standard_names.add(standard_name);
			}
			// 保存产品到数据库
			Document d = new Document();
			d.put("_id", product_id_new);
			d.put("product_name", product.getProduct_name());
			d.put("specification", product.getSpecification());
			d.put("cert_standards_ku", standard_names);
			d.put("certification", certifications);
			client.addOne("test", "product", d);
			// 保存供应商和产品的id关联映射
			Document document = new Document();
			document.put("gId", company_id_new);
			document.put("pId", product_id_new);
			client.addOne("test", "relation", document);
		}

	}

	/**
	 * 新增合同/订单
	 * 
	 * @author niyn
	 * @param orderContracts
	 */
	public void addOrderOrContract(OrderOrContract orderOrContracts,
			String user_id) {
		Document d = new Document();
		List<Purchasing> purchasingList = null;
		List<Supply> supplyList = null;
		Bson _idfilters = and(eq("contract_id",
				orderOrContracts.getContract_id()));
		ObjectId objectId = client.queryOne("test", "contract", _idfilters,
				"_id", ObjectId.class);
		// if(objectId!=null){
		// return ;
		// }
		ObjectId _id = new ObjectId();
		d.put("_id", _id);
		d.put("contract_id", orderOrContracts.getContract_id());
		d.put("company_name", orderOrContracts.getCompany_name());
		d.put("purchasing_company", orderOrContracts.getPurchasing_company());
		d.put("user_id", user_id);
		d.put("add_time", new Date());
		d.put("edit_time", new Date());
		client.addOne("test", "contract", d);

		// 订货明细
		purchasingList = orderOrContracts.getPurchasing();
		for (Purchasing purchasing : purchasingList) {
			Document p = new Document();
			p.put("p_id", _id);
			p.put("material_code", purchasing.getMaterial_code());
			p.put("material_name", purchasing.getMaterial_name());
			p.put("specification", purchasing.getSpecification());
			p.put("measurement", purchasing.getMeasurement());
			p.put("num", purchasing.getNum());
			p.put("price", purchasing.getPrice());
			p.put("total_price", purchasing.getTotal_price());
			p.put("company", purchasing.getCompany());
			client.addOne("test", "purchasing", p);
		}

		// 供货计划
		supplyList = orderOrContracts.getSupply();
		for (Supply supply : supplyList) {
			Document c = new Document();
			c.put("p_id", _id);
			c.put("material_code", supply.getMaterial_code());
			c.put("material_name", supply.getMaterial_name());
			c.put("specification", supply.getSpecification());
			c.put("measurement", supply.getMeasurement());
			c.put("num", supply.getNum());
			c.put("supply_time", supply.getSupply_time());
			c.put("address", supply.getAddress());
			c.put("person", supply.getPerson());
			client.addOne("test", "supply", c);
		}
		client.close();
	}

	/**
	 * 查询订单号/合同号列表
	 * 
	 * @author niyn
	 * @param str
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryOrderOrContract(String contract_id,
			String purchasing_company, String company_name, int start, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !("").equals(contract_id))
			condition.add(eq("contract_id", contract_id));
		if (purchasing_company != null && !("").equals(purchasing_company))
			condition.add(eq("purchasing_company", purchasing_company));
		if (company_name != null && !("").equals(company_name))
			condition.add(eq("company_name", company_name));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "contract", filters);
		List<Document> orderOrContractList = client.queryList("test",
				"contract", filters, new BasicDBObject("_id", 0),
				new BasicDBObject("add_time", -1), start, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx", orderOrContractList);
		return data.toJson();
	}

	/**
	 * 查询订单号/合同号详情
	 * 
	 * @author niyn
	 * @param _id
	 * @return
	 */
	public String queryOrderOrContractDetail(String contract_id) {
		Bson _idfilters = and(eq("contract_id", contract_id));
		List<Document> contractList = client.queryList("test", "contract",
				_idfilters, new BasicDBObject())
				.into(new ArrayList<Document>());
		ObjectId objectId = client.queryOne("test", "contract", _idfilters,
				"_id", ObjectId.class);

		Bson filters = and(eq("p_id", objectId));
		List<Document> purchasingList = client.queryList("test", "purchasing",
				filters, new BasicDBObject()).into(new ArrayList<Document>());
		List<Document> supplyList = client.queryList("test", "supply", filters,
				new BasicDBObject()).into(new ArrayList<Document>());

		// 查询已编制序列号数量
		for (Document d : supplyList) {
			Bson codeFilters = and(eq("groupId", d.get("_id").toString()));
			int codeCount = client.queryCount("test", "code", codeFilters);
			d.put("code_num", codeCount);
		}

		Document data = new Document();
		data.put("bzxx", contractList);
		data.put("purchasing", purchasingList);
		data.put("supply", supplyList);
		return data.toJson();
	}

	/**
	 * 查询订货详情
	 * 
	 * @author niyn
	 * @param materialCode
	 * @return
	 */
	public String queryPurchasingByCode(String materialCode) {
		Bson filters = and(eq("material_code", materialCode));
		List<Document> purchasingList = client.queryList("test", "purchasing",
				filters, new BasicDBObject("material_code", 0)).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("bzxx", purchasingList);
		return data.toJson();
	}

	/**
	 * 查询供货详情
	 * 
	 * @author niyn
	 * @param materialCode
	 * @return
	 */
	public String querySupplyDetailByCode(String materialCode) {
		Bson filters = and(eq("material_code", materialCode));
		List<Document> supplyList = client.queryList("test", "supply", filters,
				new BasicDBObject("material_code", 0)).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.put("bzxx", supplyList);
		return data.toJson();
	}

	/**
	 * 更新订单/合同(先删除再添加)
	 * 
	 * @author niyn
	 * @param contract_id
	 */
	public void updateOrderOrContract(OrderOrContract contract) {
		Bson filters = and(eq("contract_id", contract.getContract_id()));
		ObjectId objectId = client.queryOne("test", "contract", filters, "_id",
				ObjectId.class);
		client.deleteOne("test", "contract", filters);
		Bson paramFilters = and(eq("p_id", objectId));
		client.deleteOne("test", "purchasing", paramFilters);
		client.deleteOne("test", "supply", paramFilters);
		addOrderOrContract(contract, null);
	}
	
	/**
	 * 增加产品
	 * 
	 * @param productInfo
	 * @author zhangyb
	 */
	public void addProductInfo(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		Document d = new Document();
		d.put("company_name", productInfo.getCompany_name());
		d.put("product_identify", productInfo.getProduct_identify());
		d.put("product_name", productInfo.getProduct_name());
		d.put("specification", productInfo.getSpecification());
		d.put("measurement", productInfo.getMeasurement());
		d.put("material_code", productInfo.getMaterial_code());
		d.put("purchasing_company", productInfo.getPurchasing_company());
		d.put("add_time", productInfo.getAdd_time());
		client.addOne("test", "productInfo", d);
	}

	/**
	 * 按条件查询产品
	 * 
	 * @param company_name
	 *            企业名称
	 * @param product_identify
	 *            产品标识代码
	 * @param product_name
	 *            产品名称
	 * @param specification
	 *            specification
	 * @return
	 * @author zhangyb
	 */
	public String queryProductInfo(String company_name,
			String product_identify, String product_name, String specification,
			int start, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (company_name != null && !company_name.equals(""))
			condition.add(eq("company_name", company_name));
		if (product_identify != null && !product_identify.equals(""))
			condition.add(eq("product_identify", product_identify));
		if (product_name != null && !product_name.equals(""))
			condition.add(eq("product_name", product_name));
		if (specification != null && !specification.equals(""))
			condition.add(eq("specification", specification));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		List<Document> productInfos = new ArrayList<Document>();
		productInfos = client.queryList("test", "productInfo", filters, null,
				new BasicDBObject("add_time", -1), start, limit).into(
				new ArrayList<Document>());

		int count = client.queryCount("test", "productInfo", filters);
		Document data = new Document();
		data.put("count", count);
		data.put("productInfos", productInfos);
		return data.toJson();
	}

	/**
	 * 存库
	 * 
	 * @author zhangyb
	 * @param c
	 */
	public void addCode(Code c) {
		// TODO Auto-generated method stub
		Document d = new Document();
		d.put("code", c.getCode());
		d.put("inner_id", c.getInner_id());
		d.put("program_time", c.getProgram_time());
		d.put("purchasing_company", c.getPurchasing_company());
		d.put("contract_id", c.getContract_id());
		d.put("product_identify", c.getProduct_identify());
		d.put("product_name", c.getProduct_name());
		d.put("specification", c.getSpecification());
		d.put("material_code", c.getMaterial_code());
		d.put("company_name", c.getCompany_name());
		d.put("group_id", c.getGroup_id());
		d.put("branch_id", c.getBranch_id());
		d.put("add_time", c.getAdd_time());
		client.addOne("test", "code", d);
	}

	/**
	 * 根据组id(groupId)查询code集合
	 * 
	 * @author zhangyb
	 * @param groupId
	 * @return
	 */
	public String queryCodes(ObjectId group_id, int start, int limit) {
		// TODO Auto-generated method stub
		Bson filters = eq("group_id", group_id);
		List<Document> codes = new ArrayList<Document>();
		codes = client.queryList("test", "code", filters, null,
				new BasicDBObject("add_time", -1), start, limit).into(
				new ArrayList<Document>());

		int count = client.queryCount("test", "code", filters);
		Document d = new Document();
		d.put("count", count);
		d.put("codes", codes);
		return d.toJson();
	}

	/**
	 * 根据组id(groupId)清空数据
	 * 
	 * @author zhangyb
	 * @param groupId
	 */
	public void deleteByGroupId(String group_id) {
		// TODO Auto-generated method stub
		ObjectId groupId = new ObjectId(group_id);
		Bson filters = eq("group_id", groupId);
		client.deleteMany("test", "code", filters);
	}

	/**
	 * 查询code
	 * 
	 * @author zhangyb
	 * @param branch_id
	 * @return
	 */
	public String querySingleCode(String branch_id) {
		// TODO Auto-generated method stub
		ObjectId _id = new ObjectId(branch_id);
		Bson filter = eq("_id", _id);
		Document d = client.querySingle("test", "productInfo", filter,
				new BasicDBObject());
		// List<Document> d = new ArrayList<Document>();
		// d = client.queryList("test", "productInfo", filter,
		// new BasicDBObject()).into(new ArrayList<Document>());
		// Document data = new Document();
		// data.put("productInfo", d);
		return d.toJson();
	}

	/**
	 * 序列号信息查询
	 * 
	 * @author zhangyb
	 * @param contract_id
	 * @param state
	 * @param program_time
	 * @param purchasing_company
	 * @param company_name
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryAllCode(String contract_id, String state,
			String program_time, String purchasing_company,
			String company_name, int start, int limit) {
		// TODO Auto-generated method stub
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !contract_id.equals(""))
			condition.add(eq("contract_id", contract_id));
		if (state != null && !state.equals(""))
			condition.add(eq("state", state));
		if (program_time != null && !program_time.equals(""))
			condition
					.add(eq("program_time", TimeUtil.parserTime(program_time)));
		if (purchasing_company != null && !purchasing_company.equals(""))
			condition.add(eq("purchasing_company", purchasing_company));
		if (company_name != null && !company_name.equals(""))
			condition.add(eq("company_name", company_name));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		List<Document> codes = new ArrayList<Document>();
		codes = client.queryList("test", "code", filters, null,
				new BasicDBObject("inner_id", -1), start, limit).into(
				new ArrayList<Document>());
		int count = client.queryCount("test", "code", filters);
		Document d = new Document();
		d.put("count", count);
		d.put("codes", codes);
		return d.toJson();
	}

}
