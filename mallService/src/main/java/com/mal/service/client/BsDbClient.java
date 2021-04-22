package com.mal.service.client;

import com.alibaba.fastjson.JSON;
import com.bs.bean.beans.Command;
import com.bs.bean.beans.KVObject;
import com.bs.bean.beans.RemoteRequest;
import com.bs.bean.beans.RemoteResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class BsDbClient {

    @Autowired
    private Channel channel;


    public String set(String key, KVObject val) {
        long clientId = key.hashCode();
        RemoteRequest remoteRequest = new RemoteRequest(Command.PUT, key, val, clientId);
        ChannelFuture lastWriteFuture = channel.writeAndFlush(JSON.toJSONString(remoteRequest) + "\r\n");
        System.out.println("send msg12: " + JSON.toJSONString(remoteRequest));
        return null;
    }

    public <T> T get(String key, Class<T> clazz) {
        long clientId = key.hashCode();
        RemoteRequest remoteRequest = new RemoteRequest(Command.GET, key, null, clientId);
        channel.writeAndFlush(JSON.toJSONString(remoteRequest) + "\r\n");
        System.out.println("send msg23: " + JSON.toJSONString(remoteRequest));

        long time = System.currentTimeMillis() + 5000;
        RemoteResponse re;
        KVObject v;
        T t = null;
        while (true) {
            if (time < System.currentTimeMillis()) {
                break;
            }
            re = ClientHandler.queue.peekLast();

            if (re != null && re.getCi() == clientId) {
                if (re.getV() == null) {
                    break;
                }
                t = JSON.parseObject(re.getV().getV(), clazz);
                break;
            }
        }
        ClientHandler.queue.remove(clientId);
        return t;
    }

}
