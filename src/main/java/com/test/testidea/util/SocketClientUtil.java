package com.test.testidea.util;

import com.test.testidea.config.SocketClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import java.util.concurrent.CountDownLatch;

/**
 * 测试连接服务端socket
 *
 * @author zhangshuai
 * @date 2019/11/2 15:36
 */
public class SocketClientUtil {

    private final String host;
    private final int port;

    public SocketClientUtil() {
        this(0);
    }

    public SocketClientUtil(int port) {
        this("localhost", port);
    }

    public SocketClientUtil(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String start(String msg) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        String result = "";
        CountDownLatch lathc = new CountDownLatch(1);
        SocketClient handle = new SocketClient(lathc);
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
                        ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                        ch.pipeline().addLast(handle);
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ChunkedWriteHandler());

                    }
                });

            // 异步连接服务器
            ChannelFuture cf = b.connect(host, port).sync();

            ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
            cf.channel().writeAndFlush(buf);

            handle.getLathc().await();
            result = handle.getResult();

            // 异步等待关闭连接channel
            cf.channel().closeFuture().sync();
            // 关闭完成
            System.out.println("连接已关闭..");

        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        // 连接127.0.0.1/65535，并启动
        String result = new SocketClientUtil("127.0.0.1", 8888).start("想怎么发就怎么发");
        System.out.println("服务器返回 1:" + result);
    }
}
