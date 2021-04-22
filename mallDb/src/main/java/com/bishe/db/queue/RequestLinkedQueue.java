package com.bishe.db.queue;

import com.bishe.db.bean.DbRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * queue
 * */
public class RequestLinkedQueue implements RequestQueue {
	
	private ConcurrentLinkedQueue<DbRequest> comQueue;
	
	public RequestLinkedQueue() {
		comQueue = new ConcurrentLinkedQueue<>();
	}
	
	public void produce(DbRequest com) {
		comQueue.offer(com);
	}
	
	public DbRequest consume() {
		return comQueue.poll();
	}
	
}
