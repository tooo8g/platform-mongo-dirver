package com.platform.io.bean;

import java.util.List;

//订货明细
public class Purchasing {

	// 订单合同id
	private String p_id;
	// 物资编号
	private String material_code;
	// 物资名称
	private String material_name;
	// 规格型号
	private String specification;
	// 计量单位
	private String measurement;
	//产品表示代码
	private String product_code;
	// 订购数量
	private String num;
	// 单价
	private String price;
	// 总价
	private String total_price;
	// 生产厂家
	private String company;
	//生产厂家所在域
	private Integer company_field;
	
	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getCompany_field() {
		return company_field;
	}

	public void setCompany_field(Integer company_field) {
		this.company_field = company_field;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

}
