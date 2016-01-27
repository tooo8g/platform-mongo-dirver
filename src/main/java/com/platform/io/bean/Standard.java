package com.platform.io.bean;

public class Standard {
	
	// 标准编号
	public String standard_id;

	// 标准名称
	public String standard_name;

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}

	public String getStandard_name() {
		return standard_name;
	}

	public void setStandard_name(String standard_name) {
		this.standard_name = standard_name;
	}

	@Override
	public String toString() {
		return "Standard [standard_id=" + standard_id + ", standard_name="
				+ standard_name + "]";
	}
	
	

}
