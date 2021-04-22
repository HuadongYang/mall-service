package com.mal.service.client;


import com.alibaba.fastjson.JSON;
import com.bs.bean.beans.RemoteResponse;
import com.mal.service.bean.DataQueue;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    public static volatile DataQueue queue = new DataQueue();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println("receive11: "+msg);
        if (msg == null) {
            return;
        }
        RemoteResponse remoteResponse = JSON.parseObject(msg, RemoteResponse.class);
        queue.add(remoteResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
