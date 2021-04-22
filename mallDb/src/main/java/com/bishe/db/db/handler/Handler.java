package com.bishe.db.db.handler;


import com.bishe.db.bean.DbRequest;
import com.bishe.db.db.KVDataBase;
import com.bs.bean.beans.RemoteResponse;

// handler
public interface Handler {

	RemoteResponse process(DbRequest req);
	
	void setNextHandler(Handler h);
	
	void setDataBase(KVDataBase kvDataBase);
}
