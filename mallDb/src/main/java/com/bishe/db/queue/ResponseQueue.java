package com.bishe.db.queue;

import com.bishe.db.bean.DbResponse;

public interface ResponseQueue {

	public void produce(DbResponse rep);
	
	public DbResponse consume(long cid);
	
	public DbResponse consume(String key);

}
