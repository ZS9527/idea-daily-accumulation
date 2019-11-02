package com.test.testidea.util;

import com.test.testidea.config.SocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.Charset;

/**
 * 测试socket服务端
 *
 * @author zhangshuai
 * @date 2019/11/1 17:30
 */
public class SocketUtil {

    private final int port;

    public SocketUtil(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            // 绑定线程池
            sb.group(group, bossGroup)
                // 指定使用的channel
                .channel(NioServerSocketChannel.class)
                // 绑定监听端口
                .localAddress(this.port)
                // 绑定客户端连接时候触发操作
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("报告");
                        System.out.println("信息：有一客户端链接到本服务端");
                        System.out.println("IP:" + ch.localAddress().getHostName());
                        System.out.println("Port:" + ch.localAddress().getPort());
                        System.out.println("报告完毕");

                        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                        // 客户端触发操作
                        ch.pipeline().addLast(new SocketHandler());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                    }
                });
            // 服务器异步创建绑定
            ChannelFuture cf = sb.bind().sync();
            System.out.println(SocketUtil.class + " 启动正在监听： " + cf.channel().localAddress());
            // 关闭服务器通道
            cf.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        // 启动
        new SocketUtil(8888).start();
    }
}
