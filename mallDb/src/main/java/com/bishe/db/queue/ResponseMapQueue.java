package com.bishe.db.queue;

import com.bishe.db.bean.DbResponse;

import java.util.HashMap;

/**
 *  queue by map
 * key cid*key，key是唯一的。
 * */
public class ResponseMapQueue extends AbstractResponseQueue {

    private HashMap<String, DbResponse> reps;

    public ResponseMapQueue() {
        reps = new HashMap<>();
    }

    @Override
    public void produce(DbResponse rep) {
    }

    @Override
    public DbResponse consume(String key) {
        DbResponse rep = reps.get(key);
        if (rep != null) {
            reps.remove(key);
        }
        return rep;
    }

}

