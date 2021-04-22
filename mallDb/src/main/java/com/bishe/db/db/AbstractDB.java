package com.bishe.db.db;


import com.bishe.db.db.handler.Handler;
import com.bishe.db.queue.RequestQueue;
import com.bishe.db.queue.ResponseQueue;
import com.bishe.db.server.NetConnector;

import java.util.concurrent.atomic.AtomicLong;

// AbstractDB
public abstract class AbstractDB implements KVDataBase {


	protected RequestQueue requests;
	
	protected ResponseQueue responses;
	
	protected Thread thread;
	
	protected boolean isRunning;
	
	protected AtomicLong clientId;
	
	protected int spinCount;
	
	protected Handler handler;

	protected NetConnector netConnector;


	public RequestQueue getRequestQueue() {
		return this.requests;
	}
	
	public ResponseQueue getResponseQueue() {
		return this.responses;
	}

	public long getClientId() {
		return clientId.incrementAndGet();
	}


	public void start() throws InterruptedException {
		prepareHandlers();
		
		thread = new Thread(this);
		thread.setName("db-thread");
		thread.start();

		netConnector.start();
		System.out.println("db start");
	}
	
	protected abstract void prepareHandlers();

	public Handler getHandler(){
		return handler;
	}
	public void stop() {
		isRunning = false;
		
		requests = null;
		responses = null;
		handler = null;
	}
	


}
