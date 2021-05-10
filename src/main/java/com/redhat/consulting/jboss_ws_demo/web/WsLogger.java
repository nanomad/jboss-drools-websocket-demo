package com.redhat.consulting.jboss_ws_demo.web;

import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.web.WsEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

public class WsLogger {
    private final WsEndpoint endpoint;
    private final Logger logger;

    public WsLogger(WsEndpoint endpoint) {
        this.endpoint = endpoint;
        this.logger = LoggerFactory.getLogger(WsLogger.class.getName() + "." + endpoint.getSessionId());
    }

    public void debug(String message) {
        message(LogMessage.Level.DEBUG, message);
    }

    public void info(String message) {
        message(LogMessage.Level.INFO, message);
    }

    public void warn(String message) {
        message(LogMessage.Level.WARNING, message);
    }

    public void error(String message) {
        message(LogMessage.Level.ERROR, message);
    }

    public void message(LogMessage.Level level, String message) {
        switch (level) {
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARNING:
                logger.warn(message);
                break;
            default:
                logger.error(message);
        }
        endpoint.sendMessage(new LogMessage(level, message));
    }


}
