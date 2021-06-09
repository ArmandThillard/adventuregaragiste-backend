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
        try {
            JAXBContext cont = JAXBContext.newInstance(World.class);
            Unmarshaller u = cont.createUnmarshaller();
            //ajouter le INPUT stream (page 19)
            World world = (World) u.unmarshal(new File("src/main/resources/world.xml"));
            InputStream input=getClass().getClassLoader().getResourceAsStream("src/main/resources/world.xml");

            System.out.print(world.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void saveWordlToXml(World world) {
        
        try {
        JAXBContext cont = JAXBContext.newInstance(World.class);
        Marshaller m = cont.createMarshaller();
        //crée le nouveau fichier
        m.marshal(world, new File("newWorld.xml"));
        //crée le nouveau fichier mais est vide, à rectifier
        OutputStream output = new FileOutputStream("newWorld2.xml");
                  

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        

    }

    public World getWorld() {
        World world = new World();
            Services s = new Services();
            s.readWorldFromXml();
        return null;
    }

}
