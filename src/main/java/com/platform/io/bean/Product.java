package com.platform.io.bean;

public class Product {
	//供应商
	public String company;
	// 证书编号
	public String cert_num;
	// 产品名称
	public String product_name;
	// 规格型号
	public String specification;
	// 执行标准 从表格中读取的
	public String cert_standards;
	// 执行标准 存数据库的
	public String cert_standards_ku;
	// 资质信息
	public String certification;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCert_num() {
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
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
	public String getCert_standards() {
		return cert_standards;
	}
	public void setCert_standards(String cert_standards) {
		this.cert_standards = cert_standards;
	}
	public String getCert_standards_ku() {
		return cert_standards_ku;
	}
	public void setCert_standards_ku(String cert_standards_ku) {
		this.cert_standards_ku = cert_standards_ku;
	}
	public String getCertification() {
		return certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
	}
	@Override
	public String toString() {
		return "Product [company=" + company + ", cert_num=" + cert_num
				+ ", product_name=" + product_name + ", specification="
				+ specification + ", cert_standards=" + cert_standards
				+ ", cert_standards_ku=" + cert_standards_ku
				+ ", certification=" + certification + "]";
	}
	

	
}
