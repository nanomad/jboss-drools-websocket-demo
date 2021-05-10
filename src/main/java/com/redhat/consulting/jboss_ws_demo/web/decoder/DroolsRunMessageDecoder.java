package com.redhat.consulting.jboss_ws_demo.web.decoder;

import com.redhat.consulting.jboss_ws_demo.model.DroolsRunMessage;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class DroolsRunMessageDecoder implements Decoder.Text<DroolsRunMessage> {
    @Override
    public DroolsRunMessage decode(String s) throws DecodeException {
        return new DroolsRunMessage();
    }

    @Override
    public boolean willDecode(String s) {
        JsonStructure jsonStructure = Json.createReader(new StringReader(s))
                .read();
        JsonValue type = jsonStructure.getValue("/__type");
        return type instanceof JsonString && ((JsonString) type).getString().equals("DroolsRunMessage");
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
