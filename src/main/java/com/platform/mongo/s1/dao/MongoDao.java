package com.platform.mongo.s1.dao;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBRef;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.platform.mongo.util.Constant;

public class MongoDao {

	private MongoClient client;

	public MongoDao() {
		MongoClientURI uri = new MongoClientURI(Constant.uri);
		client = new MongoClient(uri);
	}

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

	/**
	 * select from group
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param distinct
	 * @return
	 */
	public List<Document> queryDistincValue(String db, String collection,
			Bson filters, String distinct) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);

		List<Document> cp_detail = mongocol.aggregate(
				asList(Aggregates.match(filters), Aggregates.group(distinct)))
				.into(new ArrayList<Document>());

		return cp_detail;
	}

	public List<Document> queryBysTandard(String db, String table,
			Bson filters, String string) {
		// TODO Auto-generated method stub
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(table);
		List<Document> cp_detail = mongocol.aggregate(
				asList(Aggregates.match(filters), Aggregates.group(string)))
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
	 * select from where order by limit
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
			Bson filters, Bson projected, Bson sort, int skip, int limit) {
		if (sort == null)
			return queryList(db, collection, filters, projected, skip, limit);
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (filters == null && projected == null)
			return mongocol.find().sort(sort).skip(skip).limit(limit);
		else if (filters == null && projected != null)
			return mongocol.find().sort(sort).projection(projected).skip(skip)
					.limit(limit);
		else if (filters != null && projected == null)
			return mongocol.find(filters).sort(sort).skip(skip).limit(limit);
		return mongocol.find(filters).sort(sort).projection(projected)
				.skip(skip).limit(limit);
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
		if (filters != null)
			return (int) mongocol.count(filters);
		else
			return (int) mongocol.count();
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
	 * 查询单条记录
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

	/**
	 * 查询单行记录
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param projected
	 * @return
	 */
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
	 * 查询单行记录 排序
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param projected
	 * @param sort
	 * @return
	 */
	public Document querySingle(String db, String collection, Bson filters,
			Bson projected, Bson sort) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		if (filters == null && projected == null && sort == null)
			return mongocol.find().first();
		else if (filters == null && projected == null && sort != null)
			return mongocol.find().sort(sort).first();
		else if (filters == null && projected != null && sort == null)
			return mongocol.find().projection(projected).first();
		else if (filters == null && projected != null && sort != null)
			return mongocol.find().projection(projected).sort(sort).first();
		else if (filters != null && projected == null && sort == null)
			return mongocol.find(filters).first();
		else if (filters != null && projected == null && sort != null)
			return mongocol.find(filters).sort(sort).first();
		else if (filters != null && projected != null && sort == null)
			return mongocol.find(filters).projection(projected).first();
		return mongocol.find(filters).projection(projected).sort(sort).first();
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
	public void appendOne(String db, String collection, Bson filters,
			String key, Document value) {
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
	 * 修改字段
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param key
	 * @param value
	 */
	public void updateField(String db, String collection, Bson filters,
			String key, Object value) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.updateOne(filters, new Document("$set", new Document(key,
				value)));
	}

	/**
	 * 修改记录
	 * 
	 * @param db
	 * @param collection
	 * @param filters
	 * @param values
	 */
	public void updateOne(String db, String collection, Bson filters,
			Bson values) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.updateOne(filters, new Document("$set", values));
	}
	/**
	 * 存储文件
	 * 
	 * @param db
	 * @param collection
	 * @param fileName
	 * @param data
	 */
	public void saveFile(String db, String collection, String fileName,
			byte[] data) {
		DB database = client.getDB(db);
		GridFS gf = new GridFS(database, collection);
		GridFSInputFile gfsFile = gf.createFile(data);
		gfsFile.setFilename(fileName);
		gfsFile.save();
	}

	/**
	 * 获取文件
	 * 
	 * @param db
	 * @param collection
	 * @param fileName
	 * @return
	 */
	public byte[] getFile(String db, String collection, String fileName) {
		DB database = client.getDB(db);
		GridFS gf = new GridFS(database, collection);
		GridFSDBFile gfFile = gf.findOne(fileName);
		if (gfFile != null) {
			try {
				byte[] data = new byte[(int) gfFile.getLength()];
				gfFile.getInputStream().read(data);
				return data;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 写出文件
	 * 
	 * @param db
	 * @param collection
	 * @param fileName
	 * @param path
	 */
	public void writeFile(String db, String collection, String fileName,
			String path) {
		DB database = client.getDB(db);
		GridFS gf = new GridFS(database, collection);
		GridFSDBFile gfFile = gf.findOne(fileName);
		System.out.println(gfFile);
		// try {
		// gfFile.writeTo(path);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
	
	/**
	 * 删除数据
	 * @author niyn
	 * @param db
	 * @param collection
	 * @param filters
	 */
	public void deleteOne(String db,String collection,Bson filters){
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.deleteOne(filters);
	}
	/**
	 * 删除多个
	 * @author zhangyb
	 * @param db
	 * @param collection
	 * @param groupId
	 */
	public void deleteMany(String db, String collection,Bson filters) {
		MongoDatabase database = client.getDatabase(db);
		MongoCollection<Document> mongocol = database.getCollection(collection);
		mongocol.deleteMany(filters);		
	}

	/**
	 * 关闭连接，放回连接池
	 */
	public void close() {
		client.close();
	}

}

