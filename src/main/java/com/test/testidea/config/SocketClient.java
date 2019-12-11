package com.test.testidea.config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * socket客户端
 *
 * @author zhangshuai
 * @date 2019/11/2 15:28
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SocketClient extends SimpleChannelInboundHandler<ByteBuf> {

    private CountDownLatch lathc;
    /**
     * 服务端返回的结果
     */
    private String result;

    public SocketClient(CountDownLatch lathc) {
        this.lathc = lathc;
    }

    /**
     * 向服务端发送数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端与服务端通道-开启：" + ctx.channel().localAddress() + "channelActive");
//        String sendInfo = "Hello 这里是客户端  你好啊！";
//        System.out.println("客户端准备发送的数据包：" + sendInfo);
//        // 必须有flush
//        ctx.writeAndFlush(Unpooled.copiedBuffer(sendInfo, CharsetUtil.UTF_8));

    }

    /**
     * channelInactive
     *
     * channel 通道 Inactive 不活跃的
     *
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端与服务端通道-关闭：" + ctx.channel().localAddress() + "channelInactive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        System.out.println(
            "客户端接收到的服务端信息:" + ByteBufUtil.hexDump(buf) + "; 数据包为:" + buf.toString(
                StandardCharsets.UTF_8));
        result = buf.toString(StandardCharsets.UTF_8);
        //消息收取完毕后释放同步锁 计数器减去1
        try {
            lathc.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        System.out.println("异常退出:" + cause.getMessage());
    }

}
