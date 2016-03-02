package com.platform.io.bean;

public class Code {

	public String code;// 编码
	public String inner_id;// 内部排序码
	public String program_time;// 编制时间
	public String purchasing_company;// 采购单位
	public String contract_id;// 订单合同编号
	public String product_code;// 产品标示代码
	public String materials_name;// 产品名称
	public String specifications_model;// 规格型号
	public String materials_code;// 物资编码
	public String company;// 企业名称

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

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getMaterials_name() {
		return materials_name;
	}

	public void setMaterials_name(String materials_name) {
		this.materials_name = materials_name;
	}

	public String getSpecifications_model() {
		return specifications_model;
	}

	public void setSpecifications_model(String specifications_model) {
		this.specifications_model = specifications_model;
	}

	public String getMaterials_code() {
		return materials_code;
	}

	public void setMaterials_code(String materials_code) {
		this.materials_code = materials_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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
