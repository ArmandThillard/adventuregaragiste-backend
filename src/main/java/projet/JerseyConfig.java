/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;



/**
 *
 * @author alefev02
 */
@Component
@ApplicationPath("/AdventureGaragiste")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(Webservices.class);
    }
}
