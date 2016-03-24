package com.platform.io.bean;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.mongodb.BasicDBObject;

public class Account {
	
    private String username;
	private String password;
	
	private Person person;
	private List<String> field;
	private String add_time;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<String> getField() {
		return field;
	}
	public void setField(List<String> field) {
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
