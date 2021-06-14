/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author alefev02
 */
public class Services {

    public World readWorldFromXml(String username) {
        World world = new World();
        InputStream input = null;

        try {
            input = new FileInputStream(username + "-world.xml");
        } catch (FileNotFoundException ex) {
            input = getClass().getClassLoader().getResourceAsStream("world.xml");
        } finally {

            try {
                JAXBContext cont = JAXBContext.newInstance(World.class);
                Unmarshaller u = cont.createUnmarshaller();
                world = (World) u.unmarshal(input);

                System.out.println(world.getName());
                System.out.println("username : " + username);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return world;

    }

    public void saveWordlToXml(World world, String username) {

        try {
            JAXBContext cont = JAXBContext.newInstance(World.class);
            Marshaller m = cont.createMarshaller();

            m.marshal(world, new FileOutputStream(username + "-world.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public World getWorld(String username) {

        return readWorldFromXml(username);
    }

}
