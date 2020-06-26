package com.imooc.sell.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author XuMingyue
 * @create 2020-06-24 14:55
 */
@SuppressWarnings("ALL")
@Component
@ServerEndpoint("/webSocket")
@Slf4j
@Data
public class WebSocket {

    private Session session;

    /**
     * 用set容器存储session  【不能用普通的session】
     */
    private  static CopyOnWriteArraySet<WebSocket> webSockets= new CopyOnWriteArraySet<>();

    /**
     * 对应前端事件
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSockets.add(this);
        log.info("【websocket消息】 有新的连接，总数{}",webSockets.size());

    }

    @OnClose
    public  void onClose(){
        webSockets.remove(this);
        log.info("【websocket消息】 断开连接，总数{}",webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】 收到客户端发来的消息，{}",message);
    }

    public void sendMessage(String message){
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】 广播消息，message={}",message);
            try {
                //发送消息
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
