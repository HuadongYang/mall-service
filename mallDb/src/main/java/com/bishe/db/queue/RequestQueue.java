package com.bishe.db.queue;

import com.bishe.db.bean.DbRequest;

public interface RequestQueue {

	public void produce(DbRequest req);
	
	public DbRequest consume();
	
}
