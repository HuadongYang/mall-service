package com.bishe.db.server;

import com.bishe.db.db.KVDataBase;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NetConnector implements Runnable{
    private Thread connectorThread;

    private ServerInitializer serverInitializer;

    public NetConnector(KVDataBase db) {
        this.serverInitializer = new ServerInitializer(db);
    }

    public void start() throws InterruptedException {
        connectorThread = new Thread(this);
        connectorThread.setName("Connector-Thread");

        connectorThread.start();
        System.out.println("kv connector start");
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverInitializer);
            ChannelFuture f = b.bind(8888);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
