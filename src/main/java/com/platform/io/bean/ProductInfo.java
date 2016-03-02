package com.platform.io.bean;
/**
 * 
 * @author zhangyb
 *
 */
public class ProductInfo {
	//企业名称
	public String company_name;
    //产品标识代码
	public String product_identify;
	//产品名称
	public String product_name;
	//规格型号
	public String specification;
	//计量单位
	public String measurement;
	//物资编码
	public String material_code;
	//所属路局/所属采购单位
    public String business;
    
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	public String getMaterial_code() {
		return material_code;
	}
	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	@Override
	public String toString() {
		return "ProductInfo [company_name=" + company_name
				+ ", product_identify=" + product_identify + ", product_name="
				+ product_name + ", specification=" + specification
				+ ", measurement=" + measurement + ", material_code="
				+ material_code + ", business=" + business + "]";
	}
    
    

}
