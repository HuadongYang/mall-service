package com.bishe.db.db;

import com.bishe.db.db.handler.Handler;

public interface KVDataBase{

	public void start() throws InterruptedException;
	
	Handler getHandler();

}
