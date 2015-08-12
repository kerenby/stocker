package ru.lukutin.color;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class ColorPath {

    @Inject
    ColorService colorService;

    @GET
    @Path("json-cl")
    @Produces({ "application/json" })
    public JsonObject getHelloWorldJSON() {
        return Json.createObjectBuilder()
                .add("result", colorService.createHelloMessage("Hello Color"))
                .build();
    }

    @GET
    @Path("xml-cl")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + colorService.createHelloMessage("Color") + "</result></xml>";
    }

}
