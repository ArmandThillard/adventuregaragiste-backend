/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author alefev02
 */
public class Services {

    public World readWorldFromXml() {
       World world = null;
        try {
            JAXBContext cont = JAXBContext.newInstance(World.class);
            Unmarshaller u = cont.createUnmarshaller();
            //ajouter le INPUT stream (page 19)
           world = (World) u.unmarshal(new File("src/main/resources/world.xml"));
            InputStream input=getClass().getClassLoader().getResourceAsStream("src/main/resources/world.xml");

            System.out.print(world.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return world;
        

    }

    public void saveWordlToXml(World world) {
        
        try {
        JAXBContext cont = JAXBContext.newInstance(World.class);
        Marshaller m = cont.createMarshaller();
        //crée le nouveau fichier
        m.marshal(world, new File("newWorld.xml"));
        m.marshal(world, new FileOutputStream("newWorld2.xml"));
        //crée le nouveau fichier mais est vide, à rectifier
        
                  

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        

    }

    public World getWorld() {       
            
            return readWorldFromXml();
    }

}
