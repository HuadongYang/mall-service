package com.mal.service.bean;

import com.bs.bean.beans.RemoteResponse;

import java.util.LinkedList;

public class DataQueue {

    public static volatile LinkedList<RemoteResponse> queue = new LinkedList<>();


    public void add(RemoteResponse el) {
        queue.add(el);
    }

    public RemoteResponse peekLast(){
        return queue.peekLast();
    }

    public void remove(long ci) {
        queue.removeIf(remoteResponse -> {
            return remoteResponse.getCi() == ci;
        });
    }

}
