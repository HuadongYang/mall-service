package com.bishe.db.db.handler;


import com.bishe.db.bean.DbRequest;
import com.bishe.db.bean.DbResponse;
import com.bs.bean.beans.*;

/**
 *  DataHandler
 * */
public class DataHandler extends AbstractHandler {

	private DataTable<String, KVObject> dt;

	private final KVNode<String, KVObject> none = new KVNode<>(0, (String)null, (KVObject)null, null, 0);

	public DataHandler() {
		dt = new DataTable<>();
	}

	public RemoteResponse process(DbRequest req) {
		Command type = req.getCommand();

		if (type == Command.PUT) {
			dt.put(req.getKey(), req.getValue(), req.getClientId());
			produceRepDefault(req);
		} else if (type == Command.GET) {
			DbResponse db = this.get(req.getKey(), req.getClientId());
			return prepareRemoteRep(db);
		} else if (type == Command.REMOVE) {
			dt.remove(req.getKey());
			produceRepDefault(req);
		} else if (type == Command.RESET) {
			dt.reset();
			produceRepDefault(req);
		} else if (type == Command.CLOSE) {
			dt.reset();
			produceRepDefault(req);
			dt = null;
		}
		return null;
	}

	private void produceRepDefault(DbRequest req) {
		DbResponse rep = new DbResponse();
		rep.setClientId(req.getClientId());
		rep.setKey(req.getKey());
		rep.setValue(req.getValue());

		db.getResponseQueue().produce(rep);
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

	private void produceResponse(String key, long cid, KVObject value) {
		DbResponse rep = new DbResponse();
		if (value == null) {
			rep.setClientId(cid);
			rep.setKey(key);
		} else {
			rep.setClientId(cid);
			rep.setKey(key);
			rep.setValue(value);
		}
		db.getResponseQueue().produce(rep);
	}


	public KVNode<String, KVObject> next(int index) {
		if (index <= 0 || index > Integer.MAX_VALUE) {
			throw new IllegalArgumentException();
		}

		if (index >= dt.size()) {
			return null;
		}
		KVMap.Node<String, KVObject> e = dt.getIndex(index);

		KVNode<String, KVObject> n = null;
		if (e != null) {
			n = new KVNode<String, KVObject>(e.getHash(), e.getKey(), e.getValue(), e.getNext(), e.getCid());
		}
		return n == null ? none : n;
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
