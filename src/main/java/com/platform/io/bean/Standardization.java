package com.platform.io.bean;

public class Standardization {

	// 标准类型
	public String standard_group;

	// 标准编号
	public String standard_id;

	// 标准名称
	public String standard_name;

	// 代替标准
	public String replace_id;

	// 发布日期
	public String publish_date;

	// 实施日期
	public String execute_date;

	// 标准状态
	public String standard_status;

	// 专业分类
	public String special_subject;

	//文件名称
	public String file_name;
	
	@Override
	public String toString() {
		return "Standardization [standard_group=" + standard_group
				+ ", standard_id=" + standard_id + ", standard_name="
				+ standard_name + ", replace_id=" + replace_id
				+ ", publish_date=" + publish_date + ", execute_date="
				+ execute_date + ", standard_status=" + standard_status
				+ ", special_subject=" + special_subject + "]";
	}
	

	
	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getStandard_group() {
		return standard_group;
	}

	public void setStandard_group(String standard_group) {
		this.standard_group = standard_group;
	}

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

	public String getReplace_id() {
		return replace_id;
	}

	public void setReplace_id(String replace_id) {
		this.replace_id = replace_id;
	}

	public String getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}

	public String getExecute_date() {
		return execute_date;
	}

	public void setExecute_date(String execute_date) {
		this.execute_date = execute_date;
	}

	public String getStandard_status() {
		return standard_status;
	}

	public void setStandard_status(String standard_status) {
		this.standard_status = standard_status;
	}

	public String getSpecial_subject() {
		return special_subject;
	}

	public void setSpecial_subject(String special_subject) {
		this.special_subject = special_subject;
	}
}
