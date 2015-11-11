package com.platform.mongo.s1;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.platform.mongo.util.Constant;

public class MongoDirver {

	private static final int Document = 0;
	private MongoClient client;
	private int ArrayList;

	public MongoDirver() {
		MongoClientURI uri = new MongoClientURI(Constant.uri);
		client = new MongoClient(uri);
	}

	public static void main(String[] args) {
		MongoDirver md = new MongoDirver();
		// md.test2();
		// md.test3();
		// md.test4();
		// md.test();
		// md.addCP("中厚钢板", "AS-2", "15", "企业A");
		// md.addCP("中厚钢板", "AS-2", "16", "企业A");
		 md.addCP("中厚钢板", "BS-3", "150", "企业B");
		 md.addCP("中厚钢板", "BS-4", "155", "企业B");
		// md.addCP("中厚钢板", "AS-2", "16", "企业B");
		// md.addCP("中厚钢板", "AS-3", "15", "企业C");
		// md.test5();
		// String json = md.findCP_tree(2);
		// System.out.println(md.queryCPMenu(2));
		// System.out.println(md.queryNextCPMenu(3, "11"));
		// md.queryGroupList("test", "cp_detail", new Document("group", "111"),
		// null);
		// System.out.println(md.queryCPDetail("111", 0, 10));
//		System.out.println(md.queryGYSCPMenu("561e18cbdfae591f8ceae127"));
		System.out.println(md.queryGYSCPDetail("561e18cbdfae591f8ceae127", "111", 0, 10));
		md.close();
	}

	// public void

	public void test() {
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("cp_tree1");
		FindIterable<Document> myDoc = collection.find();
		for (Document d : myDoc) {
			ArrayList<DBRef> dbref = d.get("zx", ArrayList.class);
			for (int i = 0; i < dbref.size(); i++) {
				DBRef dbr = dbref.get(i);
				System.out.println(dbr.getCollectionName());
			}
		}
	}

	public void test2() {
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("info");
		Map<String, String> value = new TreeMap<String, String>();
		value.put("name", "薄钢板");
		value.put("position", "gc-gzp.gb.bgb.cp");
		collection.updateOne(
				eq("_id", new ObjectId("55ff757384dbef18e89539ea")),
				new Document("$push", new Document("info.context", value)));
	}

	public void test3() {
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("info");
		// FindIterable<Document> myDoc = collection.find(
		// Filters.eq("info.context.name", "薄钢板")).projection(
		// new BasicDBObject("info.context.$", 1).append("_id", 0));
		FindIterable<Document> myDoc = collection.find(
				elemMatch("info.context", eq("name", "薄钢板"))).projection(
				new BasicDBObject("info.context.name", 1).append("_id", 0));
		for (Document d : myDoc) {
			System.out.println(d.toJson());
		}
	}

	public void test4() {
		MongoDatabase db = client.getDatabase("test");
		System.out.println(db.getName());
		List<Document> context = new ArrayList<Document>();
		context.add(new Document("name", "粗钢轨").append("position",
				"gc-gzp.gg.cgg.cp"));
		Document doc = new Document("name", "MongDB")
				.append("type", "database")
				.append("count", 1)
				.append("info",
						new Document("x", 203).append("y", 102).append(
								"context", context));
		MongoCollection<Document> collection = db.getCollection("info");
		collection.insertOne(doc);
	}

	public void test5() {
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("info");
		Document doc = new Document("date", new Date());
		collection.insertOne(doc);
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
	public void addCP(String cpmc, String ggxh, String jg, String qymc) {
		// 有此产品
		String id = queryOne("test", "cp_tree3", eq("zwmc", cpmc), "_id",
				String.class);
		if (id != null) {
			// 有此规格型号
			String zx_id = queryOne("test", "cp_tree3",
					and(eq("_id", id), eq("zx.zwmc", ggxh)), "_id",
					String.class);
			if (zx_id == null) {
				// 增加规格型号
				Map<String, String> value = new TreeMap<String, String>();
				value.put("zwmc", ggxh);
				appendOne("test", "cp_tree3", eq("_id", id), "zx", value);
			}
			// 有此供应商
			ObjectId gys_id = queryOne("test", "gys", eq("qymc", qymc), "_id",
					ObjectId.class);
			ObjectId cp_detail_id = new ObjectId();
			Map<String, ObjectId> gys_cp_data = new TreeMap<String, ObjectId>();
			gys_cp_data.put("cp_detail_id", cp_detail_id);
			if (gys_id == null) {
				gys_id = new ObjectId();
				// 增加供应商
				List<Map<String, ObjectId>> gys_cp_datas = new ArrayList<Map<String, ObjectId>>();
				gys_cp_datas.add(gys_cp_data);
				Document gys_data = new Document("_id", gys_id)
						.append("qymc", qymc).append("add_time", new Date())
						.append("cp", gys_cp_datas);
				addOne("test", "gys", gys_data);
			} else {
				// 增加供应商产品列表
				appendOne("test", "gys", eq("_id", gys_id), "cp", gys_cp_data);
			}
			// 将原产品注销
			updateOne(
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
			addOne("test", "cp_detail", cp_data);
		}
	}

	/**
	 * 获取菜单
	 * 
	 * @param level
	 *            菜单级别(1-3)
	 * @return
	 */
	public String queryCPMenu(int level) {
		List<Document> cp_data = queryList("test", "cp_tree" + level, null,
				new BasicDBObject("_id", 0).append("zx", 0)).into(
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
	public String queryNextCPMenu(int level, String id) {
		Document parent_data = queryOne("test", "cp_tree" + (level - 1),
				eq("_id", id), "zx");
		@SuppressWarnings("unchecked")
		List<Document> parent_ids = parent_data.get("zx", ArrayList.class);
		List<Document> child_data = queryListByArray("test", "cp_tree" + level,
				"_id", parent_ids, "id", String.class,
				new BasicDBObject("_id", 0).append("zx", 0)).into(
				new ArrayList<Document>());
		Document data = new Document("data", child_data);
		return data.toJson();
	}

	/**
	 * 获取菜单下产品
	 * 
	 * @param id
	 * @return
	 */
	public String queryCPDetail(String id, int skip, int limit) {
		Document tree3 = queryOne("test", "cp_tree3", eq("_id", id), null);
		Bson filters = and(eq("group", id), eq("valid", "1"));
		int count = queryCount("test", "cp_detail", filters);
		List<Document> cp_detail = queryList("test", "cp_detail", filters,
				new BasicDBObject("valid", 0), skip, limit).into(
				new ArrayList<Document>());
		for (Document cp : cp_detail) {
			ObjectId gys_id = cp.getObjectId("gys_id");
			Document gys = querySingle("test", "gys", eq("_id", gys_id),
					new BasicDBObject("cp", 0));
			cp.put("gys_id", gys);
		}
		tree3.append("count", count);
		tree3.put("cp", cp_detail);
		return tree3.toJson();
	}

	/**
	 * 获取供应商产品树
	 * 
	 * @param id
	 * @return
	 */
	public String queryGYSCPMenu(String id) {
		Document gys = querySingle("test", "gys", eq("_id", new ObjectId(id)),
				new BasicDBObject("cp", 1));
		List<Document> cps = gys.get("cp", ArrayList.class);
		List<ObjectId> cp_detail_ids = new ArrayList<ObjectId>();
		for (Document cp : cps)
			cp_detail_ids.add(cp.getObjectId("cp_detail_id"));
		Bson filters = in("_id", cp_detail_ids);
		List<Document> tree3s = queryDistincValue("test", "cp_detail", filters,
				"$group");
		// 逐个查找上级目录
		Document tree = new Document();
		tree.put("id", id);
		for (Document tree3_ids : tree3s) {
			String tree3_id = tree3_ids.getString("_id");
			Document tree3 = querySingle("test", "cp_tree3",
					eq("_id", tree3_id), new BasicDBObject("zwmc", 1));
			Document tree2 = querySingle("test", "cp_tree2",
					eq("zx.id", tree3_id), new BasicDBObject("zwmc", 1));
			String tree2_id = tree2.getString("_id");
			Document tree1 = querySingle("test", "cp_tree1",
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
	 * @param id
	 * @param group
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryGYSCPDetail(String id, String group, int skip, int limit) {
		Bson filters = and(eq("group", group), eq("gys_id", new ObjectId(id)),
				eq("valid", "1"));
		int count = queryCount("test", "cp_detail", filters);
		List<Document> cp_detail = queryList("test", "cp_detail", filters,
				new BasicDBObject("valid", 0), skip, limit).into(
				new ArrayList<Document>());
		Document data = new Document();
		data.append("count", count);
		data.put("cp", cp_detail);
		return data.toJson();
	}

	private String findCPMenu(int deep) {
		if (deep == 1) {
			List<Document> tree1 = queryList("test", "cp_tree1", null,
					new BasicDBObject("_id", 0).append("zx", 0)).into(
					new ArrayList<Document>());
			Document data = new Document();
			data.append("tree", tree1);
			return data.toJson();
		} else if (deep == 2) {
			List<Document> tree1s = queryList("test", "cp_tree1", null,
					new BasicDBObject("_id", 0))
					.into(new ArrayList<Document>());
			for (Document tree1 : tree1s) {
				@SuppressWarnings("unchecked")
				List<Document> tree2s = tree1.get("zx", ArrayList.class);
				List<Document> tree2 = queryListByArray("test", "cp_tree2",
						"_id", tree2s, "id", String.class,
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

	/**
	 * select from where in(,,,);
	 * 
	 * @param db
	 * @param collection
	 * @param key
	 * @param values
	 * @param inner_key
	 * @param inner_value
	 * @param projected
	 * @return
	 */
	public <T> FindIterable<Document> queryListByArray(String db,
			String collection, String key, List<Document> values,
			String inner_key, Class<T> inner_value, Bson projected) {
		List<T> filters = new ArrayList<T>();
		for (Document value : values) {
			filters.add(value.get(inner_key, inner_value));
		}

		return queryList(db, collection, in(key, filters), projected);
	}

	public List<Document> queryDistincValue(String db, String collection,
			Bson filters, String distinct) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);

		List<Document> cp_detail = mongocol.aggregate(
				asList(Aggregates.match(filters), Aggregates.group(distinct)))
				.into(new ArrayList<Document>());

		return cp_detail;
	}

	/**
	 * select from where
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param projected
	 * @return
	 */
	public FindIterable<Document> queryList(String db, String collection,
			Bson filters, Bson projected) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (filters == null && projected == null)
			return mongocol.find();
		else if (filters == null && projected != null)
			return mongocol.find().projection(projected);
		else if (filters != null && projected == null)
			return mongocol.find(filters);
		return mongocol.find(filters).projection(projected);
	}

	/**
	 * select from where limit
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param projected
	 * @param skip
	 * @param limit
	 * @return
	 */
	public FindIterable<Document> queryList(String db, String collection,
			Bson filters, Bson projected, int skip, int limit) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (filters == null && projected == null)
			return mongocol.find().skip(skip).limit(limit);
		else if (filters == null && projected != null)
			return mongocol.find().projection(projected).skip(skip)
					.limit(limit);
		else if (filters != null && projected == null)
			return mongocol.find(filters).skip(skip).limit(limit);
		return mongocol.find(filters).projection(projected).skip(skip)
				.limit(limit);
	}

	/**
	 * select count from where
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @return
	 */
	public int queryCount(String db, String collection, Bson filters) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		return (int) mongocol.count(filters);
	}

	private FindIterable<Document> queryGroupList(String db, String collection,
			Document filters, Bson projected) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		List<Document> cp_detail = mongocol.aggregate(
				asList(new Document("$match", filters),
						new Document("$group", new Document("_id", "$ggxh")
								.append("_id", "$gys_id").append("add_time",
										new Document("$max", "$add_time")))))
				.into(new ArrayList<Document>());
		Document data = new Document("data", cp_detail);
		System.out.println(data.toJson());
		return null;
	}

	/**
	 * 查询单条记录的单个字段
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T queryOne(String db, String collection, Bson filters,
			String key, Class<T> clazz) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		Document document = mongocol.find(filters)
				.projection(new BasicDBObject(key, 1)).first();
		return document != null ? document.get(key, clazz) : null;
	}

	/**
	 * 查询单条记录的单个字段
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param key
	 * @return
	 */
	public Document queryOne(String db, String collection, Bson filters,
			String key) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (key != null)
			return mongocol.find(filters).projection(new BasicDBObject(key, 1))
					.first();
		return mongocol.find(filters).first();
	}

	public Document querySingle(String db, String collection, Bson filters,
			Bson projected) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (filters == null && projected == null)
			return mongocol.find().first();
		else if (filters == null && projected != null)
			return mongocol.find().projection(projected).first();
		else if (filters != null && projected == null)
			return mongocol.find(filters).first();
		return mongocol.find(filters).projection(projected).first();
	}

	/**
	 * 追加记录
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("rawtypes")
	public void appendOne(String db, String collection, Bson filters,
			String key, Map value) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.updateOne(filters, new Document("$push", new Document(key,
				value)));
	}

	/**
	 * 新增记录
	 * 
	 * @param db
	 * @param collection
	 * @param data
	 */
	public void addOne(String db, String collection, Document data) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.insertOne(data);
	}

	/**
	 * 修改记录
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param key
	 * @param value
	 */
	public void updateOne(String db, String collection, Bson filters,
			String key, Object value) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.updateOne(filters, new Document("$set", new Document(key,
				value)));
	}

	/**
	 * 关闭连接，放回连接池
	 */
	public void close() {
		client.close();
	}

}
