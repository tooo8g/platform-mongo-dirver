package com.platform.io.bean;

import java.util.Date;
import java.util.List;
/**
 * 运单信息
 * @author Administrator
 *
 */
public class WaybillInfo {
	private  String    _id;//_id
	private  String   logistics_id;//运单号
	private  String   logistics_company;//承运公司
	private  String   car_license;//车牌号
	private  String   good_num;//货号
	private  String   send_duty;//发货人
	private  String   send_company;//企业公司
	private  String   send_phone_num;//手机号
	private  String   send_addr;//发货地址
	private  String   receive_duty;//收货人
	private  String   receive_company;//采购单位
	private  String   receive_phone_num;//手机号
	private  String   receive_addr;//收货地址
	private  String   logistics_stats;//物流状态  ( 未发货  已发货   物流运输中  已收货)
	private String send_time;//发货时间
	private String pre_send_time;//预计发货时间
	private String supply_time;//预计到货时间
	
	private Date add_time;
	private  List<Good>  goods; //货物信息
	private  List<Logistic>   logistics;//物流信息
	
	
	
	public String getSupply_time() {
		return supply_time;
	}
	public void setSupply_time(String supply_time) {
		this.supply_time = supply_time;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getPre_send_time() {
		return pre_send_time;
	}
	public void setPre_send_time(String pre_send_time) {
		this.pre_send_time = pre_send_time;
	}
	public String getLogistics_id() {
		return logistics_id;
	}
	public void setLogistics_id(String logistics_id) {
		this.logistics_id = logistics_id;
	}
	public String getLogistics_company() {
		return logistics_company;
	}
	public void setLogistics_company(String logistics_company) {
		this.logistics_company = logistics_company;
	}
	public String getCar_license() {
		return car_license;
	}
	public void setCar_license(String car_license) {
		this.car_license = car_license;
	}
	public String getGood_num() {
		return good_num;
	}
	public void setGood_num(String good_num) {
		this.good_num = good_num;
	}
	public String getSend_duty() {
		return send_duty;
	}
	public void setSend_duty(String send_duty) {
		this.send_duty = send_duty;
	}
	public String getSend_company() {
		return send_company;
	}
	public void setSend_company(String send_company) {
		this.send_company = send_company;
	}
	public String getSend_phone_num() {
		return send_phone_num;
	}
	public void setSend_phone_num(String send_phone_num) {
		this.send_phone_num = send_phone_num;
	}
	public String getSend_addr() {
		return send_addr;
	}
	public void setSend_addr(String send_addr) {
		this.send_addr = send_addr;
	}
	public String getReceive_duty() {
		return receive_duty;
	}
	public void setReceive_duty(String receive_duty) {
		this.receive_duty = receive_duty;
	}
	public String getReceive_company() {
		return receive_company;
	}
	public void setReceive_company(String receive_company) {
		this.receive_company = receive_company;
	}
	public String getReceive_phone_num() {
		return receive_phone_num;
	}
	public void setReceive_phone_num(String receive_phone_num) {
		this.receive_phone_num = receive_phone_num;
	}
	public String getReceive_addr() {
		return receive_addr;
	}
	public void setReceive_addr(String receive_addr) {
		this.receive_addr = receive_addr;
	}
	public String getLogistics_stats() {
		return logistics_stats;
	}
	public void setLogistics_stats(String logistics_stats) {
		this.logistics_stats = logistics_stats;
	}
	public List<Good> getGoods() {
		return goods;
	}
	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
	public List<Logistic> getLogistics() {
		return logistics;
	}
	public void setLogistics(List<Logistic> logistics) {
		this.logistics = logistics;
	}
	
	
	
}
