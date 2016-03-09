package com.platform.io.bean;

import java.util.Date;

public class Code {

	public String code;// 编码
	public String inner_id;// 内部排序码
	public String program_time;// 编制时间
	public String purchasing_company;// 采购单位
	public String contract_id;// 订单合同编号
	public String product_identify;// 产品标示代码
	public String product_name;// 产品名称
	public String specification;// 规格型号.
	public String material_code;// 物资编码
	public String company_name;// 企业名称.
	public String groupId;// 组Id
	public String branchId;// 关联Id
	public String state;// 状态
	public Date add_time;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInner_id() {
		return inner_id;
	}

	public void setInner_id(String inner_id) {
		this.inner_id = inner_id;
	}

	public String getProgram_time() {
		return program_time;
	}

	public void setProgram_time(String program_time) {
		this.program_time = program_time;
	}

	public String getPurchasing_company() {
		return purchasing_company;
	}

	public void setPurchasing_company(String purchasing_company) {
		this.purchasing_company = purchasing_company;
	}

	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	public String getProduct_identify() {
		return product_identify;
	}

	public void setProduct_identify(String product_identify) {
		this.product_identify = product_identify;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
