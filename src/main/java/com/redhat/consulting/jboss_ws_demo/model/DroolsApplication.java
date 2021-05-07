package com.redhat.consulting.jboss_ws_demo.model;

import com.redhat.consulting.jboss_ws_demo.web.DroolsResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;

@ApplicationPath("/api")
public class DroolsApplication extends Application {
    private HashSet<Class<?>> classes = new HashSet<Class<?>>();

    public DroolsApplication() {
        this.classes.add(DroolsResource.class);
    }

    @Override
    public HashSet<Class<?>> getClasses(){
        return classes;
    }


}
