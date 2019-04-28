package com.test.testidea.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.test.testidea.constant.DateFormatter;
import com.test.testidea.constant.Mock;
import com.test.testidea.constant.RedisKey;
import com.test.testidea.domain.user.UserRepository;
import com.test.testidea.util.Springs;
import java.io.EOFException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * WebSocket
 *
 * @author fangzhimin
 * @version 0.1
 * @date 2018/9/3 14:31
 */

@EqualsAndHashCode
@Slf4j
@Component
@ServerEndpoint(value = "/ws/{userType}/{roomId}/{rtcUserId}/{reservationId}")
public class WebSocket {

    Redis redis;
    UserRepository userRepository;

    /**
     * 忽略的异常信息片段（主机断开连接）
     */
    private final static String ERROR_LOST_MESSAGE_PART = "主机";

    /**
     * 心跳消息标识
     */
    private final static String HEART_BEAT_FLAG = "HeartBeat";

    /**
     * 记录当前在线连接数
     */
    private static LongAdder onlineCount = new LongAdder();

    /**
     * 存放每个连接对象
     */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 用户会话
     */
    private Session session;


    /**
     * 连接建立成功调用的方法
     *
     * @param session 会话
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") Long roomId,
                       @PathParam("userType") String userType, @PathParam("rtcUserId") String rtcUserId,
                       @PathParam("reservationId") Long reservationId) {
        this.session = session;

        webSocketSet.add(this);
        addOnlineCount();
        log.info(String.format("有用户接入，当前在线用户数量为：%d", getOnlineCount()));

        this.redis = Springs.getBean(Redis.class);
        this.userRepository = Springs.getBean(UserRepository.class);

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {

        webSocketSet.remove(this);
        subOnlineCount();
        log.info(String.format("有用户离开，当前在线用户数量为：%d", getOnlineCount()));

        // 更新缓存
    }

    /**
     * 发生错误时调用
     *
     * @param error   异常
     */
    @OnError
    public void onError(Throwable error) {
        // 略过不需要记录的异常日志
        if (error == null || error instanceof EOFException || error.getMessage().contains(ERROR_LOST_MESSAGE_PART)) {
            return;
        }

        log.error(error.getMessage(), error);
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
         if (Strings.isNullOrEmpty(message)) {
            return;
        }

        if (HEART_BEAT_FLAG.equals(message)) {
            send(message);
            return;
        }

        log.info("收到客户端传来的消息：" + message);

        // key, value
        String key = "key";
        String fromKey = "from";
        String toKey = "to";
        JSONObject json = JSON.parseObject(message);
        /**
         * 收到消息后的调用
         */
    }

    /**
     * 推送消息
     *
     * @param message 消息内容
     */
    private void send(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 推送消息
     *
     * @param message 消息内容
     * @param roomId 指定房间
     * @param rtcUserId 指定RTC用户
     */
    private static void send(String message, Long roomId, String rtcUserId) {
        for (WebSocket ws : webSocketSet) {
                ws.send(message);
        }
    }

    /**
     * 推送消息
     *
     * @param text 消息内容
     * @param roomId 指定房间
     *
     */
    private static void sendToRoomAdmin(String text, Long roomId) {
        for (WebSocket ws : webSocketSet) {
                JSONObject message = new JSONObject();
                message.put("key","message");
                message.put("message", text);
                message.put("datetime", LocalDateTime.now().format(DateFormatter.DATE_TIME));
                ws.send(message.toJSONString());
                break;
        }
    }

    private static long getOnlineCount() {
        return onlineCount.longValue();
    }

    private static void addOnlineCount() {
        onlineCount.increment();
    }

    private static void subOnlineCount() {
        onlineCount.decrement();
    }

}
