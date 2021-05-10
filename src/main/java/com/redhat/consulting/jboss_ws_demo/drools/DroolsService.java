package com.redhat.consulting.jboss_ws_demo.drools;

import com.redhat.consulting.jboss_ws_demo.model.LogMessage;
import com.redhat.consulting.jboss_ws_demo.model.Product;
import com.redhat.consulting.jboss_ws_demo.web.WsLogger;
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

    public void run(WsLogger logger) {
        KieServices kieServices = KieServices.Factory.get();
        logger.debug("Begin KB Container creation");
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        logger.debug("Begin KB Container verification");
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
            logger.message(logLevel, "[VERIFY] " + message);
        }
        KieBase kieBase = kContainer.getKieBase();
        KieSession session = kieBase.newKieSession();
        session.setGlobal("log", logger);
        newWebsocketLogger(session, logger);
        Product product = new Product();
        product.setType("gold");
        session.insert(product);
        session.fireAllRules();
        logger.info( "Rule engine execution completed");
    }

    private static KieRuntimeLogger newWebsocketLogger(KieRuntimeEventManager session, WsLogger logger) {
        KieWebsocketLogger kieLogger = new KieWebsocketLogger(session, logger);
        return registerRuntimeLogger(session, kieLogger);
    }

    private static KieRuntimeLogger registerRuntimeLogger(KieRuntimeEventManager session, KieWebsocketLogger logger) {
        if (session instanceof AbstractRuntime) {
            ((AbstractRuntime) session).setLogger(logger);
        }

        return logger;
    }

}
