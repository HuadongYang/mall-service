package com.bishe.db.db;



import com.bishe.db.db.handler.DataHandler;
import com.bishe.db.server.NetConnector;

import java.util.concurrent.atomic.AtomicLong;

/**
 * standlone
 */
public class StandAloneDB extends AbstractDB {
	private int lruCapacity, mapCapacity;

	public StandAloneDB(int lruCapacity, int mapCapacity) {
		this.lruCapacity = lruCapacity;
		this.mapCapacity = mapCapacity;
		clientId = new AtomicLong(1);
		netConnector = new NetConnector(this);
	}

	protected void prepareHandlers() {
		handler = new DataHandler(lruCapacity, mapCapacity);
		handler.setDataBase(this);

	}

}
