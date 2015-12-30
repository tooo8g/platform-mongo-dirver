package com.platform.io.bean;

public class Certification_Detail {
	// 产品标识代码
	public String product_code;
	// 规格型号
	public String specification;
	// 规格型号状态
	public String specification_status;
	// 产品属性名
	public String product_property_name;
	// 扩展情况
	public String expand;

	@Override
	public String toString() {
		return "	Certification_Detail [product_code=" + product_code
				+ ", specification=" + specification
				+ ", specification_status=" + specification_status
				+ ", product_property_name=" + product_property_name
				+ ", expand=" + expand + "]";
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getSpecification_status() {
		return specification_status;
	}

	public void setSpecification_status(String specification_status) {
		this.specification_status = specification_status;
	}

	public String getProduct_property_name() {
		return product_property_name;
	}

	public void setProduct_property_name(String product_property_name) {
		this.product_property_name = product_property_name;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

}
