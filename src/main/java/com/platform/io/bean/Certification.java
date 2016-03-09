package com.platform.io.bean;

import java.util.List;

public class Certification {

	// 采信目录产品范围
	public String product_range;
	// 企业名称
	public String company_name;
	// 认证单元
	public String cert_unit;
	// 认证规则名称
	public String cert_name;
	// 证书编号
	public String cert_num;
	// 颁发单位
	public String issue_organization;
	// 认证标准/技术要求
	public String cert_standards;
	// 发证日期
	public String publish_date;
	// 有效日期
	public String valid_date;
	// 证书状态
	public String cert_status;
	// 证书状态ID
	public String cert_status_id;
	// 挂接编码
	public String mount_code;
	// 项目编号
	public String project_code;
	// 组织机构代码
	public String organization_code;
	// 注册地址
	public String reg_addr;
	// 邮政编码
	public String post_code;
	// 产品类别
	public String product_kind;
	// 生产地址
	public String product_addr;
	// 公告号
	public String notification_number;
	// 证书变更情况
	public String cert_condition;
	// 证书扩展
	public String cert_expand;
	@Override
	public String toString() {
		return "Certification [product_range=" + product_range
				+ ", company_name=" + company_name + ", cert_unit=" + cert_unit
				+ ", cert_name=" + cert_name + ", cert_num=" + cert_num
				+ ", issue_organization=" + issue_organization
				+ ", cert_standards=" + cert_standards + ", publish_date="
				+ publish_date + ", valid_date=" + valid_date
				+ ", cert_status=" + cert_status + ", cert_status_id="
				+ cert_status_id + ", mount_code=" + mount_code
				+ ", project_code=" + project_code + ", organization_code="
				+ organization_code + ", reg_addr=" + reg_addr + ", post_code="
				+ post_code + ", product_kind=" + product_kind
				+ ", product_addr=" + product_addr + ", notification_number="
				+ notification_number + ", cert_condition=" + cert_condition
				+ ", cert_expand=" + cert_expand + ", remark=" + remark + "]";
	}

	// 备注
	public String remark;
	// 产品详情
	public List<Certification_Detail> cert_detail;

	public List<Certification_Detail> getCert_detail() {
		return cert_detail;
	}

	public void setCert_detail(List<Certification_Detail> cert_detail) {
		this.cert_detail = cert_detail;
	}

	public void addCert_detail(Certification_Detail detail) {
		this.cert_detail.add(detail);
	}

	public String getProduct_range() {
		return product_range;
	}

	public void setProduct_range(String product_range) {
		this.product_range = product_range;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCert_unit() {
		return cert_unit;
	}

	public void setCert_unit(String cert_unit) {
		this.cert_unit = cert_unit;
	}

	public String getCert_name() {
		return cert_name;
	}

	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getIssue_organization() {
		return issue_organization;
	}

	public void setIssue_organization(String issue_organization) {
		this.issue_organization = issue_organization;
	}

	public String getCert_standards() {
		return cert_standards;
	}

	public void setCert_standards(String cert_standards) {
		this.cert_standards = cert_standards;
	}

	public String getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}

	public String getValid_date() {
		return valid_date;
	}

	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}

	public String getCert_status() {
		return cert_status;
	}

	public void setCert_status(String cert_status) {
		this.cert_status = cert_status;
	}

	public String getCert_status_id() {
		return cert_status_id;
	}

	public void setCert_status_id(String cert_status_id) {
		this.cert_status_id = cert_status_id;
	}

	public String getMount_code() {
		return mount_code;
	}

	public void setMount_code(String mount_code) {
		this.mount_code = mount_code;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getOrganization_code() {
		return organization_code;
	}

	public void setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
	}

	public String getReg_addr() {
		return reg_addr;
	}

	public void setReg_addr(String reg_addr) {
		this.reg_addr = reg_addr;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}

	public String getProduct_addr() {
		return product_addr;
	}

	public void setProduct_addr(String product_addr) {
		this.product_addr = product_addr;
	}

	public String getNotification_number() {
		return notification_number;
	}

	public void setNotification_number(String notification_number) {
		this.notification_number = notification_number;
	}

	public String getCert_condition() {
		return cert_condition;
	}

	public void setCert_condition(String cert_condition) {
		this.cert_condition = cert_condition;
	}

	public String getCert_expand() {
		return cert_expand;
	}

	public void setCert_expand(String cert_expand) {
		this.cert_expand = cert_expand;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
