package com.platform.mongo.s2;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.platform.io.bean.Account;
import com.platform.io.bean.Code;
import com.platform.io.bean.Company;
import com.platform.io.bean.OrderOrContract;
import com.platform.io.bean.ProductInfo;
import com.platform.io.bean.WaybillInfo;
import com.platform.mongo.s2.dao.MongoDao;
import com.platform.mongo.util.DBObjectToJavaBean;
import com.platform.mongo.util.JavaBeanToDBObject;
import com.platform.mongo.util.TimeUtil;

public class MongoDirver {

	private MongoDao client;

	public MongoDirver() {
		client = new MongoDao();
	}

	public void close() {
		client.close();
	}


	/**
	 * 新增合同/订单号
	 * @author niyn
	 * @param orderOrContracts 合同/订单号
	 * @param user_id 用户名
	 * @throws Exception
	 */
	public void addOrderOrContract(OrderOrContract orderOrContracts,String user_id) throws Exception{
		ObjectId _id = new ObjectId();
		Document d= JavaBeanToDBObject.beanToDBObject(orderOrContracts);
		System.out.println("转换后的document："+d.toJson());
		List<Integer> ower =  (List<Integer>)d.remove("filed");
		List<Document> purchasing = (List<Document>)d.remove("purchasing");
		//合同订单的读写权限(无实际意义,只是在查询界面区分操作是 查看 或 编制序列号)
		List<Integer> contract_write = new ArrayList<Integer>();
		List<Integer> contract_read = new ArrayList<Integer>();
		Integer r1 = (Integer)d.get("company_field");//供应商所属域
		Integer r2 = ower.get(0);
		contract_read.add(r1);
		contract_read.add(r2);
		for (Document doc : purchasing) {
			/**
			 * 增加合同订单明细中关于订货明细的读写权限
			 */
			Document access = new Document();
			Integer w = (Integer)doc.get("company_field");
			contract_write.add(w);
			List<Integer> contract_writeList = new ArrayList<Integer>();
			contract_writeList.add(w);
			access.put("write",contract_writeList);
			access.put("read", contract_read);
			doc.put("p_id", _id);
			//TODO
			doc.put("access", access);
			System.out.println("新增订货明细json：:"+doc.toJson());
			
			client.addOne("test","purchasing", doc);
		}
		List<Document>  supply = (List<Document>)d.remove("supply");
		for (Document doc : supply) {
			doc.put("p_id", _id);
			client.addOne("test", "supply",doc);
		}
		
		
		d.put("_id", _id);
		d.put("user_id", user_id);
		d.put("add_time", new Date());
		d.put("edit_time", new Date());
		
		//TODO
		Document access = new Document();
		access.put("write", contract_write);
		access.put("read", contract_read);
		d.put("access",access);
		
		System.out.println("新增合同订单号的json--contract:"+d.toJson());
		client.addOne("test", "contract", d);
		client.close();
	}

	/**
	 * 查询订单号/合同号列表	
	 * @author niyn
	 * @param contract_id 合同/订单号
	 * @param purchasing_company 采购单位
	 * @param company_name 企业名称
	 * @param list 权限域集合
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryOrderOrContract(String contract_id,String purchasing_company,String company_name,List<Integer>list,int start, int limit) {
		/**
		 * 只取账号第一个域作为识别
		 */
		Integer key = list.get(0);
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !("").equals(contract_id))
			condition.add(eq("contract_id", contract_id));
		if (purchasing_company != null && !("").equals(purchasing_company))
			condition.add(eq("purchasing_company", purchasing_company));
		if (company_name != null && !("").equals(company_name))
			condition.add(eq("company_name", company_name));
		Bson filters = null;
		/**
		 * 将有读写权限的订单合同一并查出
		 */
		condition.add(or(in("access.write", key),in("access.read", key)));
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "contract", filters);
		List<Document>  orderOrContractList = client.queryList("test", "contract", filters, null,new BasicDBObject("add_time",-1),start,limit).into(new ArrayList<Document>());
		/**
		 * 将取出的订单合同区分出查看或编制序列号(read or write)
		 */
		for(Document d : orderOrContractList){
			Document access = (Document)d.remove("access");
			List<Integer> write = access.get("write",new ArrayList<Integer>().getClass());
			if(write.contains(key)){
				d.put("access", "write");
			}else{
				d.put("access", "read");
			}
		}
		
		
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx",orderOrContractList);
		System.out.println("查询订单合同json："+data.toJson());
		return data.toJson();
	}
	
	/**
	 * 查询订单号/合同号详情
	 * @author niyn
	 * @param contract_id 合同/订单号
	 * @param list 权限域集合
	 * @return
	 */
	public String queryOrderOrContractDetail(String _id,List<Integer> list) {
		/**
		 * 只取账号第一个域作为识别
		 */
		Integer key = list.get(0);
		ObjectId objectId = new ObjectId(_id);
		Bson _idfilters = and(eq("_id",objectId));
		Document contractList = client.queryOne("test", "contract", _idfilters, null);
		/**
		 * 将有读写权限的订单合同一并查出
		 */
		Bson filters = and(eq("p_id",objectId),or(in("access.write", key),in("access.read", key)));
		List<Document> purchasingList = client.queryList("test", "purchasing", filters,  null).into(new ArrayList<Document>());
		List<Document> supplyList = client.queryList("test","supply" , filters,null).into(new ArrayList<Document>());
		/**
		 * 将取出的订单合同区分出查看或编制序列号(read or write)
		 */
		for(Document d : purchasingList){
			Document access = (Document)d.remove("access");
			List<Integer> write = access.get("write",new ArrayList<Integer>().getClass());
			if(write.contains(key)){
				d.put("access", "write");
			}else{
				d.put("access", "read");
			}
		}
		
		//查询已编制序列号数量
			for (Document d : supplyList) {
				Bson codeFilters = and(eq("branchId",d.get("_id").toString() ));
				int codeCount = client.queryCount("test", "code", codeFilters);
				d.put("code_num", codeCount);
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+d.toJson());
			}
		Document data = new Document();
		data.put("bzxx",contractList);
		data.put("purchasing", purchasingList);
		data.put("supply", supplyList);
		System.out.println("订单合同详情json："+data.toJson());
		return data.toJson();
	}
	/**
	 * 查询订货详情
	 * @author niyn 
	 * @param materialCode  [material] 物资编号
	 * @return
	 */
	public String queryPurchasingByCode(String materialCode) {
		Bson filters = and(eq("material_code",materialCode));
		List<Document> purchasingList = client.queryList("test", "material", filters,null).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("wzxx", purchasingList);
		return data.toJson();
	}
	
	/**
	 * 查询订货详情
	 * @author niyn
	 * @param _id   id  [purchasing]
	 * @return
	 */
	public String queryPurchasingById(String _id) {
		Bson filters = and(eq("_id",new ObjectId(_id)));
		Document purchasingList = client.queryOne("test", "purchasing", filters,null);
		Document data = new Document();
		data.put("wzxx", purchasingList);
		return data.toJson();
	}
	
	/**
	 * 查询供货详情
	 * @author niyn
	 * @param materialCode  物资编号
	 * @return
	 */
	public String querySupplyDetailByCode(String materialCode) {
		Bson filters = and(eq("material_code",materialCode));
		List<Document> supplyList = client.queryList("test", "supply", filters,  new BasicDBObject("material_code",0)).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("bzxx", supplyList);
		return data.toJson();
	}

	/**
	 * 更新订单/合同(先删除再添加)
	 * @author niyn
	 * @param contract_id  合同/订单号
	 * @throws Exception 
	 */
	public void  updateOrderOrContract(OrderOrContract contract) throws Exception{
		 Bson filters = and(eq("contract_id",contract.getContract_id()));
		 ObjectId objectId = client.queryOne("test", "contract", filters, "_id",ObjectId.class);
		 client.deleteOne("test", "contract", filters);
		 Bson paramFilters = and( eq("p_id",objectId));
		 client.deleteOne("test","purchasing", paramFilters);
		 client.deleteOne("test","supply", paramFilters);
		 addOrderOrContract(contract,null);
	}
	/**
	 * 增加产品
	 * @param productInfo 
	 * @author zhangyb
	 */
	public void addProductInfo(ProductInfo productInfo) throws Exception {
		Document d  = JavaBeanToDBObject.beanToDBObject(productInfo);
		d.put("add_time", new Date());
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
	 *            规格型号
	 * @param list  权限域集合
	 * @return
	 * @author zhangyb
	 */
	public String queryProductInfo(String company_name, String product_identify, String product_name,
			String specification, List<Integer> list,int start, int limit) {
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
		condition.add(in("filed",list));
		if (condition.size() > 0)
			filters = and(condition);
		List<Document> productInfos = new ArrayList<Document>();
		productInfos = client
				.queryList("test", "productInfo", filters, null, new BasicDBObject("add_time", -1), start, limit)
				.into(new ArrayList<Document>());

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
	public void addCode(Code c) throws Exception {
		Document d = JavaBeanToDBObject.beanToDBObject(c);
		client.addOne("test", "code", d);
	}

	/**
	 * 根据组id(groupId)查询code集合
	 * 
	 * @author zhangyb
	 * @param groupId
	 * @return
	 */
	public String queryCodes(ObjectId bId,List<Integer> list,int start, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (bId != null && !bId.equals(""))
			condition.add(eq("branch_id", bId));
		Bson filters = null;
		condition.add(in("filed",list));
		if (condition.size() > 0)
			filters = and(condition);
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
	public void deleteByGroupId(String groupId,List<Integer> list) {
		Bson filters = and(eq("groupId", groupId),in("filed",list));
		client.deleteMany("test", "code", filters);
	}

	/**
	 * 查询code
	 * @author zhangyb
	 * @param branchId 
	 * @param list  权限域集合
	 * @return
	 */
	public String querySingleCode(String branchId,List<Integer> list) {
		ObjectId _id = new ObjectId(branchId);
		Bson filter = and(eq("_id", _id),in("filed",list));
		Document d = client.querySingle("test", "productInfo", filter, new BasicDBObject());
		return d.toJson();
	}

	/**
	 * 序列号信息查询
	 * 
	 * @author zhangyb
	 * @param contract_id 合同订单编号
	 * @param state 状态 0-未打印 1-已打印（默认是未打印）
	 * @param program_time 编制时间
	 * @param purchasing_company采购单位
	 * @param company_name 企业名称.
	 * @param list 权限域集合
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryAllCode(String contract_id, String state, String program_time, String purchasing_company,
			String company_name, List<Integer> list,int start, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !contract_id.equals(""))
			condition.add(eq("contract_id", contract_id));
		if (state != null && !state.equals(""))
			condition.add(eq("state", state));
		if (program_time != null && !program_time.equals(""))
			condition.add(eq("program_time", TimeUtil.parserTime(program_time)));
		if (purchasing_company != null && !purchasing_company.equals(""))
			condition.add(eq("purchasing_company", purchasing_company));
		if (company_name != null && !company_name.equals(""))
			condition.add(eq("company_name", company_name));
		Bson filters = null;
		condition.add(in("filed",list));
		if (condition.size() > 0)
			filters = and(condition);
		List<Document> codes = new ArrayList<Document>();
		codes = client.queryList("test", "code", filters, null, new BasicDBObject("inner_id", -1), start, limit)
				.into(new ArrayList<Document>());
		int count = client.queryCount("test", "code", filters);
		Document d = new Document();
		d.put("count", count);
		d.put("codes", codes);
		return d.toJson();
	}

	/**
	 * 新增运单信息
	 * @author niyn
	 * @param waybillInfo
	 */
	public void addWaybillInfo(WaybillInfo waybillInfo) throws Exception {
		List<Integer> wList =  waybillInfo.getFiled();
		Document d = JavaBeanToDBObject.beanToDBObject(waybillInfo);
		System.out.println("======================"+d.toJson());
		ObjectId _id = new ObjectId();
		d.put("_id", _id);
		List<Document>  goods =(List<Document>)d.remove("goods");
		for (Document doc : goods) {
			doc.put("p_id", _id);
			doc.put("filed", wList);
			client.addOne("test","goods", doc);
		}
		d.put("add_time", new Date());
		d.put("filed",wList);
		client.addOne("test", "waybillInfo", d);
		client.close();
	}

	/**
	 * 查询运单【条件查询】
	 * @author niyn
	 * @param logistics_id  运单号
	 * @param logistics_company  承运公司
	 * @param car_license 车牌号
	 * @param contract_id 订单合同编号
	 * @param logistics_stats 物流状态 ( 未发货 已发货 物流运输中 已收货)
	 * @param good_num 货号 
	 * @param list 权限域集合
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryWaybillInfo(String logistics_id, String logistics_company, String car_license,
			String contract_id, String logistics_stats, String good_num, List<Integer> list,int start, int limit) {
		List<Bson> condition = new ArrayList<Bson>();
		if (logistics_id != null && !("").equals(logistics_id))
			condition.add(eq("logistics_id", logistics_id));
		if (logistics_company != null && !("").equals(logistics_company))
			condition.add(eq("logistics_company", logistics_company));
		if (car_license != null && !("").equals(car_license))
			condition.add(eq("car_license", car_license));
		if (logistics_stats != null && !("").equals(logistics_stats))
			condition.add(eq("logistics_stats", logistics_stats));
		if (good_num != null && !("").equals(good_num))
			condition.add(eq("good_num", good_num));
		if (contract_id != null && !("").equals(contract_id))	{
			Bson cfilters = eq("contract_id", contract_id);
			ObjectId _id =  client.queryOne("test", "waybillInfo", cfilters, "_id", ObjectId.class);
			condition.add(eq("_id",_id));
		}
		condition.add(in("filed", list));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "waybillInfo", filters);
		List<Document>  waybillInfoList = client.queryList("test", "waybillInfo", filters, new BasicDBObject(),new BasicDBObject("add_time",-1),start,limit).into(new ArrayList<Document>());	
		Document data = new Document();
		data.put("count", count);
		data.put("waybillInfo",waybillInfoList);
		System.out.println("运单列表json："+data.toJson());
		return data.toJson();
	}
	
	/**
	 * 查询货物信息
	 * @author niyn
	 * @param _id
	 * @param list 权限域集合
	 * @param start
	 * @param limit
	 * @return
	 */
	public String queryGoodsInfo(String _id,List<Integer>  list,Integer start,Integer limit){
		Bson f= and(eq("p_id",new ObjectId(_id)),in("filed",list));
		int count = client.queryCount("test", "goods", f);
		List<Document> goodsList = client.queryList("test", "goods", f,  new BasicDBObject(),start,limit).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("goodsList",goodsList);
		return data.toJson();
	} 
	
	
	/**
	 * 查询物流信息
	 * @author niyn
	 * @param _id
	 * @param list 权限域集合
	 * @return
	 */
	public String queryLogisticsInfo(String _id,List<Integer>  list){
		Bson f= and(eq("p_id",new ObjectId(_id)),in("filed",list));
		int count = client.queryCount("test", "logistics", f);
		List<Document> logisticsList = client.queryList("test","logistics" , f,new BasicDBObject()).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("logisticsList",logisticsList);
		return data.toJson();
	}

	/**
	 * 查询用户信息
	 * @author niyn
	 * @param name 用户名
	 * @param password 密码
	 * @return
	 */
	public Account login(String name, String password) throws Exception {
		Bson filters = and (eq("name",name),eq("password",password));
		Document data = client.querySingle("test", "account", filters, null);
		Account a = new Account();
		a  = DBObjectToJavaBean.propertySetter(data, Account.class);
		System.out.println(a);
		if(data==null){
			System.out.println("数据为空");
		}
		return a;
	} 
	
	
	/**
	 * 新增公司
	 * @author niyn
	 */
	public void addCompany(Company company) throws Exception{
		Document  d = JavaBeanToDBObject.beanToDBObject(company);
		int i = client.incrementing("test","company");
		d.put("com_filed",i);
		d.put("add_time", new Date());
		System.out.println(d.toJson());
		client.addOne("test","company", d);
	}

	/**
	 * 查询公司列表
	 * @author niyn
	 * @param com_name 公司名称
	 * @param org_code 机构代码
	 * @param list 
	 * @param skip
	 * @param limit
	 * @return
	 */
	public String queryCompany(String com_name,String org_code,int skip,int limit){
		List<Bson> condition = new ArrayList<Bson>();
		if (com_name != null && !("").equals(com_name))
			condition.add(regex("com_name", "^.*" + com_name + ".*$"));
		if (org_code != null && !("").equals(org_code))
			condition.add(regex("org_code", "^.*" + org_code + ".*$"));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "company", filters);
		List<Document> companyList = client.queryList("test", "company", filters,null,new BasicDBObject("add_time",-1), skip, limit).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("companyList",companyList);
		System.out.println(data.toJson());
		return data.toJson();
	}
	
	/**
	 * 新增用户
	 * @author niyn
	 */
	public void addAccount(Account account) throws Exception{
		Document d = JavaBeanToDBObject.beanToDBObject(account);
		d.put("add_time", new Date());
	    List<Integer> oper_filedList = new ArrayList<Integer>();
		d.put("oper_filed", oper_filedList);
		System.out.println("新增用户的json："+d.toJson());
		client.addOne("test","account", d);
	}
	/**
	 * 
	 * @author niyn
	 * @param name  登录名
	 * @param username 用户名
	 * @param company 所属公司
	 * @return
	 */
	public String queryAccount(String name,String username, String company,int skip,int limit){
		List<Bson> condition = new ArrayList<Bson>();
		if (name != null && !("").equals(name))
			condition.add(eq("name", name));
		if (username != null && !("").equals(username))
			condition.add(eq("person.username", username));
		if (company != null && !("").equals(company))
			condition.add(eq("person.company", company));
		Bson filters = null;
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "account", filters);
		List<Document> accountList = client.queryList("test", "account", filters,null,new BasicDBObject("add_time",-1), skip, limit).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("count", count);
		data.put("accountList",accountList);
		System.out.println("用户列表的json："+data.toJson());
		return data.toJson();
	}
	
	/**
	 * 查询用户的权限信息[filed]
	 * @throws Exception 
	 */
	public String queryAuthorityInfo(String _id) throws Exception{
		Bson filters = and(eq("_id",new ObjectId(_id)));
		//用户持有的公司权限
		Document com = client.querySingle("test", "account", filters, null);
		List<Integer> accountFiledList = (List<Integer>) com.get("filed");
		//所有公司
		List<Document> companyList = client.queryList("test", "company", null,new BasicDBObject(), 0, 100).into(new ArrayList<Document>());
		List<Object> allComList = new ArrayList<Object>();
		for (Document doc : companyList) {
			allComList.add(doc.get("com_filed"));
		}
		allComList.removeAll(accountFiledList);
		//未分配的公司的权限
		List<Object> unassignedComList = allComList;
		Document data = new Document();
		List<Document>  assignedList = new ArrayList<Document>();
		List<Document> unassignedList = new ArrayList<Document>();
		for (Integer o : accountFiledList) {
				Bson f = and(eq("com_filed",o));
				Document doc = client.querySingle("test", "company", f, null);
				if(doc!=null){
					Document assignedDoc = new Document();
					assignedDoc.put("com_filed", o.toString());
					assignedDoc.put("com_name",(String)doc.get("com_name"));
					assignedList.add(assignedDoc);
				}
		}
		for (Object o : unassignedComList) {
			Bson f2 = and(eq("com_filed",o));
			Document doc = client.querySingle("test", "company", f2, null);
			if(doc!=null){
				Document unassignedDoc = new Document();
				unassignedDoc.put("com_filed", o.toString());
				unassignedDoc.put("com_name",(String)doc.get("com_name"));
				unassignedList.add(unassignedDoc);
			}  
		}
		data.put("assignedList", assignedList);
		data.put("unassignedList", unassignedList);
		System.out.println(data.toJson());
		return data.toJson();
	}
	
	/**
	 * 分配权限[filed]
	 * @author niyn
	 * @param fileds 权限数组
	 * @param user_id 用户id
	 */
	public void assign(String[] fileds,String user_id){
		Bson f = and(eq("_id",new ObjectId(user_id)));
		Document doc = client.querySingle("test", "account", f, null);
		List<Integer>  filedList = (List<Integer>) doc.get("filed");
		filedList.clear();
		if((fileds.length>0)&&fileds!=null){
			for (String filed : fileds) {//右-->左
				filedList.add(Integer.valueOf(filed));
			}
		}
		System.out.println(filedList);
		client.updateField("test", "account", f, "filed", filedList);
	}
	/**
	 * 存储PDF入库 
	 * @author niyn
	 */
	public void addPDF(String filename,byte[] data){
		client.saveFile("test", "pdf",filename,data);
	}
	
	/**
	 * 下载PDF文件
	 * @author niyn
	 * @throws Exception 
	 */
	public GridFSDBFile downloadPDF(String filename,String path) throws Exception{
		return client.writeFile("test", "pdf", filename, path);
	}
	
	/**
	 * 查询用户的操作信息[operation]
	 * @throws Exception 
	 */
	public String queryOperationInfo(String _id) throws Exception{
		Bson filters = and(eq("_id",new ObjectId(_id)));
		//用户持有的公司权限
		Document com = client.querySingle("test", "account", filters, null);
		List<Integer> accountOperFiledList = (List<Integer>) com.get("oper_filed");
		//所有公司
		List<Document> operationList = client.queryList("test", "operation", null,new BasicDBObject(), 0, 100).into(new ArrayList<Document>());
		List<Object> allComList = new ArrayList<Object>();
		for (Document doc : operationList) {
			allComList.add(doc.get("oper_num"));
		}
		allComList.removeAll(accountOperFiledList);
		//未分配的公司的权限
		List<Object> unassignedComList = allComList;
		Document data = new Document();
		List<Document>  assignedList = new ArrayList<Document>();
		List<Document> unassignedList = new ArrayList<Document>();
		for (Integer o : accountOperFiledList) {
				Bson f = and(eq("oper_num",o));
				Document doc = client.querySingle("test", "operation", f, null);
				if(doc!=null){
					Document assignedDoc = new Document();
					assignedDoc.put("oper_num", o.toString());
					assignedDoc.put("oper_code",(String)doc.get("oper_code"));
					assignedDoc.put("oper_name",(String)doc.get("oper_name"));
					assignedList.add(assignedDoc);
				}
		}
		for (Object o : unassignedComList) {
			Bson f2 = and(eq("oper_num",o));
			Document doc = client.querySingle("test", "operation", f2, null);
			if(doc!=null){
				Document unassignedDoc = new Document();
				unassignedDoc.put("oper_num", o.toString());
				unassignedDoc.put("oper_code",(String)doc.get("oper_code"));
				unassignedDoc.put("oper_name",(String)doc.get("oper_name"));
				unassignedList.add(unassignedDoc);
			}  
		}
		data.put("assignedList", assignedList);
		data.put("unassignedList", unassignedList);
		System.out.println(data.toJson());
		return data.toJson();
	}
	
	/**
	 * 分配权限[operation]
	 * @author niyn
	 * @param fileds 权限数组
	 * @param user_id 用户id
	 */
	public void assignOperation(String[] oper_fileds,String user_id){
		Bson f = and(eq("_id",new ObjectId(user_id)));
		Document doc = client.querySingle("test", "account", f, null);
		List<Integer>  operFiledsList = (List<Integer>) doc.get("oper_filed");
		operFiledsList.clear();
		if((oper_fileds.length>0)&&oper_fileds!=null){
			for (String filed : oper_fileds) {//右-->左
				operFiledsList.add(Integer.valueOf(filed));
			}
		}
		System.out.println(operFiledsList);
		client.updateField("test", "account", f, "oper_filed", operFiledsList);
	}
	
	public static void main(String[] args) {
		MongoDirver m = new MongoDirver();
		String a = "5714cb0b38d23f1420e989a9";
		a = m.queryPurchasingById(a);
		System.out.println(a);
	}
}


