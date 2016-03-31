package com.platform.mongo.s2;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.platform.io.bean.Account;
import com.platform.io.bean.Code;
import com.platform.io.bean.Company;
import com.platform.io.bean.OrderOrContract;
import com.platform.io.bean.Person;
import com.platform.io.bean.ProductInfo;
import com.platform.io.bean.WaybillInfo;
import com.platform.mongo.s2.dao.MongoDao;
import com.platform.mongo.util.DBObjectToJavaBean;
import com.platform.mongo.util.JavaBeanToDBObject;
import com.platform.mongo.util.MD5Util;
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
		List<Integer> fList =  orderOrContracts.getFiled();
		ObjectId _id = new ObjectId();
		Document d= JavaBeanToDBObject.beanToDBObject(orderOrContracts);
		Document purchasing = new Document();
		purchasing.put("purchasing", d.remove("purchasing"));
		purchasing.put("p_id", _id);
		purchasing.put("filed", fList);
		client.addOne("test","purchasing", purchasing);
		Document supply = new Document();
		supply.put("supply", d.remove("supply"));
		supply.put("p_id", _id);
		supply.put("filed",fList);
		client.addOne("test", "supply",supply);
		d.put("_id", _id);
		d.put("user_id", user_id);
		d.put("add_time", new Date());
		d.put("edit_time", new Date());
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
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !("").equals(contract_id))
			condition.add(eq("contract_id", contract_id));
		if (purchasing_company != null && !("").equals(purchasing_company))
			condition.add(eq("purchasing_company", purchasing_company));
		if (company_name != null && !("").equals(company_name))
			condition.add(eq("company_name", company_name));
		Bson filters = null;
		condition.add(in("filed", list));
		if (condition.size() > 0)
			filters = and(condition);
		int count = client.queryCount("test", "contract", filters);
		List<Document>  orderOrContractList = client.queryList("test", "contract", filters, null,new BasicDBObject("add_time",-1),start,limit).into(new ArrayList<Document>());	
		Document data = new Document();
		data.put("count", count);
		data.put("bzxx",orderOrContractList);
		return data.toJson();
	}
	
	/**
	 * 查询订单号/合同号详情
	 * @author niyn
	 * @param contract_id 合同/订单号
	 * @param list 权限域集合
	 * @return
	 */
	public String queryOrderOrContractDetail(String contract_id,List<Integer> list) {
		Bson _idfilters = and(eq("contract_id",contract_id));
		List<Document> contractList = client.queryList("test", "contract", _idfilters, new BasicDBObject()).into(new ArrayList<Document>());
		ObjectId objectId = client.queryOne("test", "contract", _idfilters, "_id",ObjectId.class);
		
		Bson filters = and(eq("p_id",objectId),in("filed", list));
		List<Document> purchasingList = client.queryList("test", "purchasing", filters,  new BasicDBObject()).into(new ArrayList<Document>());
		List<Document> supplyList = client.queryList("test","supply" , filters,new BasicDBObject()).into(new ArrayList<Document>());
		
		//查询已编制序列号数量
			for (Document d : supplyList) {
				Bson codeFilters = and(eq("branchId",d.get("_id").toString() ));
				int codeCount = client.queryCount("test", "code", codeFilters);
				d.put("code_num", codeCount);
			}

		Document data = new Document();
		data.put("bzxx",contractList);
		data.put("purchasing", purchasingList);
		data.put("supply", supplyList);
		System.out.println(data.toJson());
		return data.toJson();
	}
	/**
	 * 查询订货详情
	 * @author niyn
	 * @param materialCode  物资编号
	 * @return
	 */
	public String queryPurchasingByCode(String materialCode) {
		Bson filters = and(eq("material_code",materialCode));
		List<Document> purchasingList = client.queryList("test", "purchasing", filters,  new BasicDBObject("material_code",0)).into(new ArrayList<Document>());
		Document data = new Document();
		data.put("bzxx", purchasingList);
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
		// TODO Auto-generated method stub
//		Document d = new Document();
//		d.put("company_name", productInfo.getCompany_name());
//		d.put("product_identify", productInfo.getProduct_identify());
//		d.put("product_name", productInfo.getProduct_name());
//		d.put("specification", productInfo.getSpecification());
//		d.put("measurement", productInfo.getMeasurement());
//		d.put("material_code", productInfo.getMaterial_code());
//		d.put("purchasing_company", productInfo.getPurchasing_company());
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
//		Document d = new Document();
//		d.put("code", c.getCode());
//		d.put("inner_id", c.getInner_id());
//		d.put("program_time", c.getProgram_time());
//		d.put("purchasing_company", c.getPurchasing_company());
//		d.put("contract_id", c.getContract_id());
//		d.put("product_identify", c.getProduct_identify());
//		d.put("product_name", c.getProduct_name());
//		d.put("specification", c.getSpecification());
//		d.put("material_code", c.getMaterial_code());
//		d.put("company_name", c.getCompany_name());
//		d.put("groupId", c.getGroup_id());
//		d.put("branchId", c.getBranch_id());
//		d.put("add_time", c.getAdd_time());
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
	public String queryCodes(ObjectId gId, ObjectId bId,List<Integer> list,int start, int limit) {
		// TODO Auto-generated method stub
		List<Bson> condition = new ArrayList<Bson>();
		if (gId != null && !gId.equals(""))
			condition.add(eq("group_id", gId));
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
		// TODO Auto-generated method stub
		ObjectId _id = new ObjectId(branchId);
		Bson filter = and(eq("_id", _id),in("filed",list));
		Document d = client.querySingle("test", "productInfo", filter, new BasicDBObject());
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
		// TODO Auto-generated method stub
		List<Bson> condition = new ArrayList<Bson>();
		if (contract_id != null && !contract_id.equals(""))
			condition.add(eq("contract_id", contract_id));
		if (state != null && !state.equals(""))
			condition.add(eq("state", state));
		if (program_time != null && !program_time.equals(""))
			condition.add(eq("program_time", program_time));
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
		Document goods = new Document();
		goods.put("goods", d.remove("goods"));
		goods.put("p_id", _id);
		goods.put("filed", wList);
		client.addOne("test","goods", goods);
		Document logistics = new Document();
		logistics.put("logistics", d.remove("logistics"));
		logistics.put("p_id", _id);
		logistics.put("op_time", new Date());
		logistics.put("filed", wList);
		client.addOne("test","logistics", logistics);
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
//		Bson codefilters = and(eq("org_code",company.getOrg_code()));
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
			condition.add(eq("com_name", com_name));
		if (org_code != null && !("").equals(org_code))
			condition.add(eq("org_code", org_code));
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
	 * 查询用户的权限信息
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
	 * 分配权限
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
	
	
	
	public static void main(String[] args) throws Exception {
		MongoDirver m = new MongoDirver();
		Company c = new Company();
//		Person p = new  Person();
//		p.setAddress(null);
//		p.setAge(age);
//		p.setCompany(company);
//		p.setEmail(email);
//		p.setName(name);
//		p.setTel(tel);
		
//		公司名称  组织机构代码 公司地址 联系人 联系方式
		c.setCom_name("中国");
		c.setCom_addr("北京");
		c.setCon_way("123456789");
		c.setCon_person("联系人test");
		c.setOrg_code("001");
//		m.addCompany(c);
		
		String _id = "56fa466c0c5880c110a25a3b";
//		m.queryAuthorityInfo(_id);
		
		
		String com_name = "";
		String org_code = "";
		List<Integer> list = new ArrayList<Integer>();
		Integer skip = 0;
		Integer limit = 2;
//		m.queryCompany(com_name, org_code, skip, limit);
		
		String name = "";
		String username = "";
		String company = "";
		
//		m.queryAccount(name, username, company, skip, limit);
		
		
		Account a = new Account();
		Person p = new  Person();
		p.setAddress("北京");
		p.setAge("10");
		p.setCompany("中铁");
		p.setEmail("123qq.com");
		p.setUsername("niyn");
		p.setTel("123456789");
		List<Integer> filed = new ArrayList<Integer>();
		filed.add(3);
		filed.add(1);
		filed.add(2);
		a.setName("niyn");
		a.setPassword("123");
		a.setPerson(p);
		a.setFiled(filed);
		
		m.addAccount(a);
		
		String[] fileds = {"5","6"};
		
//		m.assign(fileds, "56fa466c0c5880c110a25a3b");
	}
	
}


