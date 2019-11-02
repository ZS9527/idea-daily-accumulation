package com.test.testidea.util;

import com.test.testidea.config.SocketClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 客户端socket
 *
 * @author zhangshuai
 * @date 2019/11/2 15:44
 */
public class SocketClientUtil {

    private final String host;
    private final int port;

    public SocketClientUtil(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 注册线程池
            b.group(group)
                // 使用NioSocketChannel来作为连接用的channel类
                .channel(NioSocketChannel.class)
                // 绑定连接端口和host信息
                .remoteAddress(new InetSocketAddress(this.host, this.port))
                // 绑定连接初始化器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        System.out.println("正在连接中...");
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                        ch.pipeline().addLast(new SocketClient());
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ChunkedWriteHandler());

                    }
                });
            System.out.println("服务端连接成功..");

            // 异步连接服务器
            ChannelFuture cf = b.connect().sync();
            // 连接完成
            System.out.println("服务端连接成功...");
            // 异步等待关闭连接channel
            cf.channel().closeFuture().sync();
            // 关闭完成
            System.out.println("连接已关闭..");

        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        // 连接127.0.0.1/65535，并启动
        new SocketClientUtil("127.0.0.1", 8888).start();

    }
}
