package com.bishe.db.db;



import com.bishe.db.bean.DbRequest;
import com.bishe.db.db.handler.DataHandler;
import com.bishe.db.queue.RequestQueue;
import com.bishe.db.queue.ResponseQueue;
import com.bishe.db.server.NetConnector;
import com.bs.bean.beans.Command;

import java.util.concurrent.atomic.AtomicLong;

/**
 *  standlone
 * */
public class StandAloneDB extends AbstractDB{

	public StandAloneDB(int initCapacity, RequestQueue req,
				 ResponseQueue rep) {
		clientId = new AtomicLong(1);
		netConnector = new NetConnector(this);
		
		this.requests = req;
		this.responses = rep;
		
		this.isRunning = true;
	}

	protected void prepareHandlers() {
		handler = new DataHandler();
		handler.setDataBase(this);

	}

	@Override
	public void run() {
		while (isRunning) {
			DbRequest req;
			while ((req = requests.consume()) == null) {
				spinCount++;
			}
			
			Command com = req.getCommand();
			if (com == Command.PUT || com == Command.GET
					|| com == Command.REMOVE || com == Command.RESET) {
				handler.process(req);
			} else if (com == Command.CLOSE) {
				handler.process(req);
				this.stop();
			} else {
				System.out.println("wrong request type " + req.getCommand());
			}
			req = null;  // gc db req except expire and watch
		}
		System.out.println("db stop " + spinCount);
	}

}
