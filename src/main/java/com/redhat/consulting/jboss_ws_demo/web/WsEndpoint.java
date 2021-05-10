package com.redhat.consulting.jboss_ws_demo.web;

import com.redhat.consulting.jboss_ws_demo.drools.DroolsService;
import com.redhat.consulting.jboss_ws_demo.model.DroolsRunMessage;
import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.model.WsConnectedMessage;
import com.redhat.consulting.jboss_ws_demo.web.decoder.DroolsRunMessageDecoder;
import com.redhat.consulting.jboss_ws_demo.web.encoder.LogMessageEncoder;
import com.redhat.consulting.jboss_ws_demo.web.encoder.WsConnectedMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

@ServerEndpoint(
        value = "/ws",
        encoders = {LogMessageEncoder.class, WsConnectedMessageEncoder.class},
        decoders = {DroolsRunMessageDecoder.class}
)
public class WsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(WsEndpoint.class);
    private final DroolsService service;

    private String sessionId;
    private WsLogger wsLogger;
    private Session session;

    @Inject
    public WsEndpoint(DroolsService service) {
        this.service = service;
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        this.sessionId = UUID.randomUUID().toString();
        logger.info("onOpen called, generated session id: {}", sessionId);
        this.wsLogger = new WsLogger(this);
        try {
            session.getBasicRemote().sendObject(new WsConnectedMessage(sessionId));
        } catch (EncodeException e) {
            logger.error("Could not send session id back to front-end", e);
        }
    }

    @OnMessage
    public void onRunMessage(Session session, DroolsRunMessage message) throws IOException {
        logger.info("DroolsRunMessage received from front-end: {}", message);
        service.run(wsLogger);
        logger.info("Drools execution completed");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // Ideally, we would like to stop drools ... but we can't :(
        logger.info("WS closed. Session id was: {}", sessionId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.error("WS error. Session id {}.", sessionId, throwable);
    }

    public void sendMessage(LogMessage msg) {
        try {
            this.session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
