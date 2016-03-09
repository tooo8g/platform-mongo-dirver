package com.platform.io.bean;

/**
 * 资质-铁路总公司铁路专用产品认证采信
 * 
 * @author zhanglei
 * 
 */
public class TreeStruct {

	public String id;

	public String name;

	public String value;

	public String pid;

	public char py_char;

	public TreeStruct() {

	}

	public TreeStruct(String id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public char getPy_char() {
		return py_char;
	}

	public void setPy_char(char py_char) {
		this.py_char = py_char;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
