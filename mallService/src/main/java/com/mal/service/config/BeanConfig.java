package com.mal.service.config;

import com.mal.service.client.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
public class BeanConfig {

    @Value("${bs.db.ip}")
    private String bsDbIp;

    @Value("${bs.db.port}")
    private int bsDbPort;
    @Autowired
    private DataSource dataSource;


    @Bean
    public Channel getChannel() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            Channel ch = b.connect(bsDbIp, bsDbPort).sync().channel();
            return ch;
        } finally {

        }
    }
    @Bean
    public DataModel getDataModel(){
        DataModel dataModel = new MySQLJDBCDataModel(dataSource, "user_mall_detail", "userId", "mallId", "preferenceValue", "timestamp");
        return dataModel;
    }
}
