package com.platform.io.bean;

public class Price {

	// 物资名称
	public String name;
	// 规格型号
	public String specification;
	// 材质
	public String texture;
	// 厂家
	public String company;
	// 地区
	public String area;
	// 城市
	public String city;
	// 价格日期
	public String date;
	// 价格
	public int price;
	// 计价方式
	public String priceType;
	// 备注
	public String expand;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(String price) {
		try {
			if (price != null && !price.equals("")) {
				this.price = Integer.parseInt(price);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
