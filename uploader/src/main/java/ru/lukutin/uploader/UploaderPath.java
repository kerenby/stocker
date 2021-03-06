package ru.lukutin.uploader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Sergey on 5/25/2015.
 */
@Path("/")
public class UploaderPath {
    @GET
    @Path("json-up")
    @Produces({ "application/json" })
    public JsonObject getHelloWorldJSON() {
        return Json.createObjectBuilder()
                .add("result", "Hello Uploader")
                .build();
    }

    @GET
    @Path("xml-up")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + "Uploader" + "</result></xml>";
    }

}
