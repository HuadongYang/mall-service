package com.bishe.db.db;

import com.bishe.db.db.handler.Handler;
import com.bishe.db.queue.RequestQueue;
import com.bishe.db.queue.ResponseQueue;

public interface KVDataBase extends Runnable{

	public void start() throws InterruptedException;
	
	public void stop();

	public RequestQueue getRequestQueue();

	public ResponseQueue getResponseQueue();

	public long getClientId();

	Handler getHandler();


}
