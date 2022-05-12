package com.thegreatapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("/jsongreet")
public class GreetingResource {

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String greet(Data data) {
        switch (data.getLanguage()) {
            case "Portuguese":
                return "{ \"greeting\" : \"Saudações, " + data.getName() + "\" }";
            case "Spanish":
                return "{ \"greeting\" : \"Saludos, " + data.getName() + "\" }";
            default:
                return "{ \"greeting\" : \"Hello, " + data.getName() + "\" }";
        }
    }
}