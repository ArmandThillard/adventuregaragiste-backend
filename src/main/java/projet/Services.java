/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author alefev02
 */
public class Services {
    public World readWorldFromXml(){
        try {JAXBContext cont = JAXBContext.newInstance(World.class);
            Unmarshaller u = cont.createUnmarshaller();
            World world = (World) u.unmarshal(new File("src/main/resources/world.xml"));
            
            System.out.print(world.getName());
            
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        return null;
        
    }
    
    public void saveWordlToXml(World world) {
        Marshaller m = cont.createMarshaller();
        m.marshal(world, new File("newliste.xml"));
    }
    
    public World getWorld(){
        return null;
    }
           
    
    
    
}
