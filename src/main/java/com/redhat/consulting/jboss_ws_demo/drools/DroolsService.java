package com.redhat.consulting.jboss_ws_demo.drools;

import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.model.Product;
import com.redhat.consulting.jboss_ws_demo.web.WsEndpoint;
import org.drools.core.impl.AbstractRuntime;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.event.KieRuntimeEventManager;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DroolsService {

    public void run(WsEndpoint endpoint) {
        KieServices kieServices = KieServices.Factory.get();
        endpoint.sendMessage(new LogMessage(LogMessage.Level.DEBUG, "Begin KB Container creation"));
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        endpoint.sendMessage(new LogMessage(LogMessage.Level.DEBUG, "Begin KB Container verification"));
        Results verificationResult = kContainer.verify();
        for (Message message : verificationResult.getMessages()) {
            final LogMessage.Level logLevel;
            switch (message.getLevel()) {
                case INFO:
                    logLevel = LogMessage.Level.INFO;
                    break;
                case ERROR:
                    logLevel = LogMessage.Level.ERROR;
                    break;
                case WARNING:
                    logLevel = LogMessage.Level.WARNING;
                    break;
                default:
                    logLevel = LogMessage.Level.DEBUG;
            }
            endpoint.sendMessage(new LogMessage(logLevel, "[VERIFY] " + message));
        }
        KieBase kieBase = kContainer.getKieBase();
        KieSession session = kieBase.newKieSession();
        newWebsocketLogger(session, endpoint);
        Product product = new Product();
        product.setType("gold");
        session.insert(product);
        session.fireAllRules();
        endpoint.sendMessage(new LogMessage(LogMessage.Level.INFO, "Rule engine execution completed"));
    }

    private static KieRuntimeLogger newWebsocketLogger(KieRuntimeEventManager session, WsEndpoint endpoint) {
        KieWebsocketLogger logger = new KieWebsocketLogger(session, endpoint);
        return registerRuntimeLogger(session, logger);
    }

    private static KieRuntimeLogger registerRuntimeLogger(KieRuntimeEventManager session, KieRuntimeLogger logger) {
        if (session instanceof AbstractRuntime) {
            ((AbstractRuntime) session).setLogger(logger);
        }

        return logger;
    }

}
