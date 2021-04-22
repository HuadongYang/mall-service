package com.bs.bean.beans;

import com.alibaba.fastjson.annotation.JSONField;

public class KVObject {

	private DataType t; // type
	
	private String v; // value
	
	public KVObject() {}

	public KVObject(DataType t, String v) {
		this.t = t;
		this.v = v;
	}

	@JSONField(serialize = false)
	public DataType getT() {
		return t;
	}

	@JSONField(deserialize = false)
	public void setT(DataType type) {
		this.t = type;
	}
	
	@JSONField(name = "t")
	public int getTCode() {
		return t.getVal();
	}
	
	@JSONField(name = "t")
	public void setTCode(int type) {
		this.t = DataType.getType(type);
	}

	public String getV() {
		return v;
	}

	public void setV(String value) {
		this.v = value;
	}
	
}
