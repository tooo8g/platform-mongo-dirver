package com.platform.io.bean;

import java.util.Date;
import java.util.List;
//订单、合同
public class OrderOrContract {
	
	
	private String _id;//_id
	private String contract_id;//订单、合同号
	private String company_name;//企业名称
	private String purchasing_company;//采购单位
	private Date add_time;//增加时间
	private String edit_time;//编辑时间
	private String user_id;//编制人员
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	private List<Purchasing> purchasing;
	private List<Supply> supply;
	
    public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	
	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getPurchasing_company() {
		return purchasing_company;
	}

	public void setPurchasing_company(String purchasing_company) {
		this.purchasing_company = purchasing_company;
	}

	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public List<Purchasing> getPurchasing() {
		return purchasing;
	}

	public void setPurchasing(List<Purchasing> purchasing) {
		this.purchasing = purchasing;
	}

	public List<Supply> getSupply() {
		return supply;
	}

	public void setSupply(List<Supply> supply) {
		this.supply = supply;
	}
	

	

}
