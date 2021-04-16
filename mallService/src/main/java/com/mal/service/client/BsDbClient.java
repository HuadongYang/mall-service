package com.mal.service.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BsDbClient {

    @Autowired
    private Channel channel;

    public void send(String line) throws InterruptedException {
        ChannelFuture lastWriteFuture = channel.writeAndFlush(line + "\r\n");
        if (lastWriteFuture != null) {
            lastWriteFuture.sync();
        }
    }

    public String get(String line, String key) throws InterruptedException {
        Integer size = ClientHandler.queue.size();

        ChannelFuture lastWriteFuture = channel.writeAndFlush(line + "\r\n");
//        if (lastWriteFuture != null) {
//            lastWriteFuture.sync();
//        }
        long time = System.currentTimeMillis() + 3000;
        while (true) {
            if (ClientHandler.queue.size() <= size) {
                continue;
            }
            size ++;
            String msg = ClientHandler.queue.get(size-1);
            String[] msgArr = msg.split("\\s");
            if (msgArr[0].equals(key)) {
                ClientHandler.queue.remove(size-1);
                if (msgArr[1].equals("null")) {
                    return null;
                }
                return msgArr[1];
            }
            if (time < System.currentTimeMillis()) {
                return null;
            }
        }


    }
}
