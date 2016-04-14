package com.platform.io.bean;

/**
 * 物资
 * @author zhanglei
 *
 */
public class Material {

	//物资编码
	public String material_code;
	//物资名称
	public String material_name;
	//规格型号
	public String specification;
	//计量单位
	public String measurement;
	
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
