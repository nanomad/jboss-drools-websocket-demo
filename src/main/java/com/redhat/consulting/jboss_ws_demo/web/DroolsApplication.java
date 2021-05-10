package com.redhat.consulting.jboss_ws_demo.web;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;

@ApplicationPath("/api")
public class DroolsApplication extends Application {
    private HashSet<Class<?>> classes = new HashSet<Class<?>>();

    public DroolsApplication() {
    }

    @Override
    public HashSet<Class<?>> getClasses(){
        return classes;
    }


}
