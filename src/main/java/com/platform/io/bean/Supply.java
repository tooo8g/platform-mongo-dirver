package com.platform.io.bean;

//供货计划
public class Supply {
	private String p_id;
	private String material_code;// 物资编号
	private String material_name;// 物资名称
	private String specification;// 规格型号
	private String measurement;// 计量单位
	private String num;//供货数量
	private String supply_time;//交货时间
	private String address;//交货地点
	private String person;//收货人
	private String code_num;//已编制的序列号数量
	
	
	
	public String getCode_num() {
		return code_num;
	}
	public void setCode_num(String code_num) {
		this.code_num = code_num;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getSupply_time() {
		return supply_time;
	}
	public void setSupply_time(String supply_time) {
		this.supply_time = supply_time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	
}
