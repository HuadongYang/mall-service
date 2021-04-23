package com.bishe.db.db;


import com.bishe.db.db.handler.Handler;
import com.bishe.db.server.NetConnector;

import java.util.concurrent.atomic.AtomicLong;

// AbstractDB
public abstract class AbstractDB implements KVDataBase {

	protected AtomicLong clientId;
	

	protected Handler handler;

	protected NetConnector netConnector;


	public void start() throws InterruptedException {
		prepareHandlers();

		netConnector.start();
		System.out.println("db start");
	}
	
	protected abstract void prepareHandlers();

	public Handler getHandler(){
		return handler;
	}

	


}
