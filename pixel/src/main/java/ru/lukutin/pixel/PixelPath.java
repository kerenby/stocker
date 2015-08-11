package ru.lukutin.pixel;

//import ru.lukutin.uploader.UploaderService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Sergey on 5/25/2015.
 */
@Path("/")
public class PixelPath {

    @Inject
    PixelService pixelService;

//    @Inject
//    UploaderService uploaderService;

    @GET
    @Path("json")
    @Produces({ "application/json" })
    public JsonObject getHelloWorldJSON() {
        return Json.createObjectBuilder()
                .add("result", pixelService.createHelloMessage("Hello Katya"))
                .build();
    }

    @GET
    @Path("xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + pixelService.createHelloMessage("World") + "</result></xml>";
    }

}
