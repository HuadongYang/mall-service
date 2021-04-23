package com.bishe.db.server;

import com.alibaba.fastjson.JSON;
import com.bishe.db.bean.DbRequest;
import com.bishe.db.db.KVDataBase;
import com.bs.bean.beans.RemoteRequest;
import com.bs.bean.beans.RemoteResponse;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {


    private KVDataBase db;

    public ServerHandler(KVDataBase db) {
        this.db = db;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("receive message: " + msg);
        RemoteRequest req = (RemoteRequest) JSON.parseObject(msg, RemoteRequest.class);
        RemoteResponse remoteResponse = db.getHandler().process(new DbRequest(req.getC(), req.getK(), req.getV(), req.getCi()));
        if (remoteResponse != null) {
            System.out.println("send message: " + JSON.toJSONString(remoteResponse));
            ctx.write(JSON.toJSONString(remoteResponse) + "\r\n");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
