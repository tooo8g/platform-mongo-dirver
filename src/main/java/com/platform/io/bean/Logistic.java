package com.platform.io.bean;

import java.util.Date;

public class Logistic {
	private String p_id;
	private String  send_location;//始发地
	private String  receive_location;//目的地
	private Date  op_time;//处理时间
	private String  op_situation;	//处理情况
	private String  point_situation;//站点情况
	private String  op_person;//操作人
	
	
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getSend_location() {
		return send_location;
	}
	public void setSend_location(String send_location) {
		this.send_location = send_location;
	}
	public String getReceive_location() {
		return receive_location;
	}
	public void setReceive_location(String receive_location) {
		this.receive_location = receive_location;
	}
	public Date getOp_time() {
		return op_time;
	}
	public void setOp_time(Date op_time) {
		this.op_time = op_time;
	}
	public String getOp_situation() {
		return op_situation;
	}
	public void setOp_situation(String op_situation) {
		this.op_situation = op_situation;
	}
	public String getPoint_situation() {
		return point_situation;
	}
	public void setPoint_situation(String point_situation) {
		this.point_situation = point_situation;
	}
	public String getOp_person() {
		return op_person;
	}
	public void setOp_person(String op_person) {
		this.op_person = op_person;
	}
	
	
}
