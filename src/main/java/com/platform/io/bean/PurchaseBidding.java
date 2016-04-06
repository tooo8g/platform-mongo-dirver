package com.platform.io.bean;

public class PurchaseBidding {
	
	//采购编号
	private String purchaseOrderNo;

	//采购名称   
	private String purchaserName;

	//组织单位   
	private String purchaserCompany;

    //公告类型   
	private String  announcementType;

	//采购品种   
	private String  purchaserVariety;

	//采购地区   
	private String  purchaserArea;

	//招标文件获取时间  
	private String purchaserFileGetTime;

	//发布时间  
	private String publishTime;

	//数据来源   
	private String dataSource;
	
	//地址
	private String address;
	
	//所属行业
	private String industry;
	
	private String addTime;
	
	private String editTime;
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserCompany() {
		return purchaserCompany;
	}

	public void setPurchaserCompany(String purchaserCompany) {
		this.purchaserCompany = purchaserCompany;
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getPurchaserVariety() {
		return purchaserVariety;
	}

	public void setPurchaserVariety(String purchaserVariety) {
		this.purchaserVariety = purchaserVariety;
	}

	public String getPurchaserArea() {
		return purchaserArea;
	}

	public void setPurchaserArea(String purchaserArea) {
		this.purchaserArea = purchaserArea;
	}

	public String getPurchaserFileGetTime() {
		return purchaserFileGetTime;
	}

	public void setPurchaserFileGetTime(String purchaserFileGetTime) {
		this.purchaserFileGetTime = purchaserFileGetTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}
