package com.bishe.db.db.handler;


import com.bishe.db.bean.DbRequest;
import com.bishe.db.bean.DbResponse;
import com.bs.bean.beans.*;

/**
 *  DataHandler
 * */
public class DataHandler extends AbstractHandler {

	private DataTable<String, KVObject> dt;

	public DataHandler(int lruCapacity, int mapCapacity) {
		dt = new DataTable<>(lruCapacity, mapCapacity);
	}

	public RemoteResponse process(DbRequest req) {
		Command type = req.getCommand();

		if (type == Command.PUT) {
			dt.put(req.getKey(), req.getValue(), req.getClientId());
		} else if (type == Command.GET) {
			DbResponse db = this.get(req.getKey(), req.getClientId());
			return prepareRemoteRep(db);
		} else if (type == Command.REMOVE) {
			dt.remove(req.getKey());
		} else if (type == Command.RESET) {
			dt.reset();
		} else if (type == Command.CLOSE) {
			dt.reset();
			dt = null;
		}
		return null;
	}

	private DbResponse get(String key, long clientId) {
		KVObject v = dt.get(key);
		DbResponse rep = new DbResponse();
		if (v == null) {
			rep.setClientId(clientId);
			rep.setKey(key);
		} else {
			rep.setClientId(clientId);
			rep.setKey(key);
			rep.setValue(v);
		}
		return rep;
	}


	private RemoteResponse prepareRemoteRep(DbResponse dp) {
		RemoteResponse rep = new RemoteResponse();

		rep.setCi(dp.getClientId());
		rep.setD(dp.isDirty());
		rep.setK(dp.getKey());
		rep.setM(dp.isMove());
		rep.setV(dp.getValue());

		dp = null;  // gc db rep
		return rep;
	}


}
