package com.redhat.consulting.jboss_ws_demo.drools;

import com.redhat.consulting.jboss_ws_demo.web.WsLogger;
import org.drools.core.audit.WorkingMemoryLogger;
import org.drools.core.audit.event.LogEvent;
import org.kie.api.event.KieRuntimeEventManager;
import org.kie.api.logger.KieRuntimeLogger;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

class KieWebsocketLogger extends WorkingMemoryLogger implements KieRuntimeLogger {
    private final WsLogger logger;

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    public KieWebsocketLogger(KieRuntimeEventManager session, WsLogger logger) {
        super(session);
        this.logger = logger;
    }

    public void logEventCreated(LogEvent logEvent) {
        logger.info(logEvent.toString());
    }

    public void close() {
    }
}
