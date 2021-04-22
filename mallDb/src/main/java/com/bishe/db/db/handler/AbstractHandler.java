package com.bishe.db.db.handler;


import com.bishe.db.db.KVDataBase;

public abstract class AbstractHandler implements Handler {

	protected Handler next;
	
	protected KVDataBase db;
	
	@Override
	public void setNextHandler(Handler h) {
		this.next = h;
	}
	
	public void setDataBase(KVDataBase db) {
		this.db = db;
	}
	

}
