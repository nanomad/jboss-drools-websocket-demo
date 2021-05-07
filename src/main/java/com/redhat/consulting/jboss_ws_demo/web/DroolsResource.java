package com.redhat.consulting.jboss_ws_demo.web;

import com.redhat.consulting.jboss_ws_demo.drools.DroolsService;
import com.redhat.consulting.jboss_ws_demo.model.StartMessage;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drools")
public class DroolsResource {

    @Inject
    WsSessionHolder sessionHolder;

    @Inject
    DroolsService droolsService;

    @POST
    @Path("/run")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response runDrools(StartMessage message) {
        sessionHolder.get(message.getSessionId()).ifPresent(e -> droolsService.run(e));
        return Response.status(200).build();
    }
}
