package com.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/getGreeting")
public class InternationalGreetingResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting getGreeting(Data data) {
        switch (data.getLanguage()) {
            case "Portuguese":
                return new Greeting("Saudações do Serverless Workflow, " + data.getName() + "!");
            case "Spanish":
                return new Greeting("Saludos desde Serverless Workflow, " + data.getName() + "!");
            default:
                return new Greeting("Greetings from Serverless Workflow, " + data.getName() + "!");
        }
    }
}