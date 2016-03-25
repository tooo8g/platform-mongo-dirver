package com.platform.io.bean;

import java.util.List;

public class Account {
	
    private String username;
	private String password;
	
	private Person person;
	private List<Integer> field;//权限字段
	private String add_time;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Integer> getField() {
		return field;
	}
	public void setField(List<Integer> field) {
		this.field = field;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
