package com.platform.io.bean;

import java.util.List;

public class Account {
	private String _id;
    private String name;//登录名
	private String password;//密码
	private String confirm_pwd;//确认密码
	private Person person;
	private List<Integer> filed;//数据权限字段
	private List<Integer> oper_filed;//操作权限字段
	private String add_time;//注册时间
	
	public List<Integer> getOper_filed() {
		return oper_filed;
	}
	public void setOper_filed(List<Integer> oper_filed) {
		this.oper_filed = oper_filed;
	}
	public String getConfirm_pwd() {
		return confirm_pwd;
	}
	public void setConfirm_pwd(String confirm_pwd) {
		this.confirm_pwd = confirm_pwd;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Integer> getFiled() {
		return filed;
	}
	public void setFiled(List<Integer> filed) {
		this.filed = filed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	
}
