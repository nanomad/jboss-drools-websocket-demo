package com.redhat.consulting.jboss_ws_demo.web.encoder;

import com.redhat.consulting.jboss_ws_demo.model.LogMessage;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LogMessageEncoder implements Encoder.Text<LogMessage> {

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public String encode(LogMessage object) throws EncodeException {
        return Json.createObjectBuilder()
                .add("__type", "LogMessage")
                .add("level", String.valueOf(object.getLevel()))
                .add("message", object.getMessage())
                .build()
                .toString();
    }

    @Override
    public void destroy() {

    }
}
