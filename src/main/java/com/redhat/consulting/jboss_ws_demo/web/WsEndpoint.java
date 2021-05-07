package com.redhat.consulting.jboss_ws_demo.web;

import com.redhat.consulting.jboss_ws_demo.web.encoder.LogMessageEncoder;
import com.redhat.consulting.jboss_ws_demo.web.encoder.WsConnectedMessageEncoder;
import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.model.WsConnectedMessage;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

@ServerEndpoint(value = "/ws", encoders = {LogMessageEncoder.class, WsConnectedMessageEncoder.class})
public class WsEndpoint {


    private Session session;
    private String id;

    @Inject
    WsSessionHolder holder;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        String id = UUID.randomUUID().toString();
        this.id = id;
        holder.start(id, this);
        try {
            session.getBasicRemote().sendObject(new WsConnectedMessage(id));
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        holder.stop(id);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    public void sendMessage(LogMessage msg) {
        try {
            this.session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
