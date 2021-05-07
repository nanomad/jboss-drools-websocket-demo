package com.redhat.consulting.jboss_ws_demo.web;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class WsSessionHolder {

    private final Map<String, WsEndpoint> sessions = new ConcurrentHashMap<>();

    public void start(String id, WsEndpoint session) {
        sessions.put(id, session);
    }

    public void stop(String id) {
        sessions.remove(id);
    }

    public Optional<WsEndpoint> get(String id) {
        return Optional.ofNullable(sessions.get(id));
    }
}
