package com.redhat.consulting.jboss_ws_demo.web.encoder;

import com.redhat.consulting.jboss_ws_demo.model.WsConnectedMessage;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WsConnectedMessageEncoder implements Encoder.Text<WsConnectedMessage> {

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public String encode(WsConnectedMessage object) throws EncodeException {
        return Json.createObjectBuilder()
                .add("__type", "WsConnectedMessage")
                .add("id", object.getId())
                .build()
                .toString();
    }

    @Override
    public void destroy() {

    }
}
