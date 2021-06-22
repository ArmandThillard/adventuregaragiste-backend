/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author alefev02
 */
@Path("generic")
public class Webservices {
    Services services;

    public Webservices() {
        services = new Services();
    }

    @GET
    @Path("world")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getWorld(@Context HttpServletRequest request) {
        String username = request.getHeader("X-user");
        return Response.ok(services.getWorld(username)).build();
    }

    @PUT
    @Path("product")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void putProduct(@Context HttpServletRequest request, @RequestBody ProductType p) {
        String username = request.getHeader("X-user");
        services.updateProduct(username, p);

    }

    @PUT
    @Path("manager")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void putManager(@Context HttpServletRequest request, @RequestBody PallierType newmanager) {

        String username = request.getHeader("X-user");

        this.services.updateManager(username, newmanager);
    }

    @PUT
    @Path("upgrade")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void putUpgrade(@Context HttpServletRequest request, @RequestBody PallierType newupgrade) {

        String username = request.getHeader("X-user");

        this.services.updateUpgrade(username, newupgrade);

    }

    @PUT
    @Path("angelupgrade")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void putAngelUpgrade(@Context HttpServletRequest request, @RequestBody PallierType newangelupgrade) {

        String username = request.getHeader("X-user");

        this.services.updateAngelUpgrade(username, newangelupgrade);

    }

    @DELETE
    @Path("world")
    public void deleteWorld(@Context HttpServletRequest request) {
        String username = request.getHeader("X-user");

        this.services.resetWorld(username);
    }
}
