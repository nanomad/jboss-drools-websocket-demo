package com.redhat.consulting.jboss_ws_demo.drools;

import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.web.WsEndpoint;
import org.drools.core.audit.WorkingMemoryConsoleLogger;
import org.drools.core.audit.WorkingMemoryLogger;
import org.drools.core.audit.event.LogEvent;
import org.kie.api.event.KieRuntimeEventManager;
import org.kie.api.logger.KieRuntimeLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

class KieWebsocketLogger extends WorkingMemoryLogger implements KieRuntimeLogger {
    protected static final transient Logger logger = LoggerFactory.getLogger(WorkingMemoryConsoleLogger.class);
    private final WsEndpoint endpoint;

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    public KieWebsocketLogger(KieRuntimeEventManager session, WsEndpoint endpoint) {
        super(session);
        this.endpoint = endpoint;
    }

    public void logEventCreated(LogEvent logEvent) {
        endpoint.sendMessage(new LogMessage(LogMessage.Level.INFO, logEvent.toString()));
    }

    public void close() {
    }
}
