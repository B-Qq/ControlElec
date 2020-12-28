package com.example.controlelec.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                log.info("发送数据:" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 给指定用户发送信息
     */
    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接成功调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName) {
        sessionPools.put(userName, session);
        addOnlineCount();
        log.info("用户:" + userName + " 加入webSocket！当前客户端数为:" + onlineNum);
//        try {
//            sendMessage(session, "欢迎" + userName + "加入连接！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName) {
        sessionPools.remove(userName);
        subOnlineCount();
        log.info("用户:" + userName + " 断开webSocket连接！当前客户端数为:" + onlineNum);
    }

    /**
     * 收到客户端信息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        message = "收到客户端消息：" + message;
        log.info(message);
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 错误时调用
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("webSocket 发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
}
