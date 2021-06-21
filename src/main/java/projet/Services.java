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

    public Boolean updateManager(String username, PallierType newmanager) {

        // aller chercher le monde qui correspond au joueur
        World world = this.getWorld(username);
        // trouver dans ce monde, le manager équivalent à celui passé
        // en paramètre
        int managerIndex = 0;
        for (PallierType m : world.managers.pallier) {
            if (newmanager.getName() == m.getName()) {
                managerIndex = world.managers.pallier.indexOf(m);
            }
        }

        PallierType manager = world.managers.pallier.get(managerIndex);
        if (manager == null) {
            return false;
        } else {
            manager.setUnlocked(true);
        }

        // trouver le produit correspondant au manager
        ProductType product = world.products.getProduct().get(manager.getIdcible());
        if (product == null) {
            return false;
        }

        // soustraire de l'argent du joueur le cout du manager
        world = this.calcMoney(world);

        // sauvegarder les changements au monde
        this.saveWordlToXml(world, username);
        return true;
    }

    public World calcMoney(World world) {
        long spentTime = System.currentTimeMillis() - world.getLastupdate();
        for (ProductType p : world.products.product) {
            if (p.isManagerUnlocked()) {
                spentTime += p.getTimeleft();
                world.money += Math.floor(spentTime / p.getVitesse()) * (p.getQuantite() * p.getRevenu()
                        * (1 + (world.getActiveangels() * world.getAngelbonus()) / 100));
                p.timeleft = spentTime % p.getVitesse();
            } else {
                if (p.timeleft != 0) {
                    if (spentTime < p.getTimeleft()) {
                        p.setTimeleft(p.getTimeleft() - spentTime);
                    } else {
                        world.money += p.getQuantite() * p.getRevenu()
                                * (1 + (world.getActiveangels() * world.getAngelbonus()) / 100);
                    }
                }
            }
        }
        return world;
    }

    public Boolean updateProduct(String username, ProductType newproduct) {
        World world = getWorld(username);

        ProductType product = findProductById(world, newproduct.getId());

        if (product == null) {
            return false;
        }

        int qtchange = newproduct.getQuantite() - product.getQuantite();
        if (qtchange > 0) {
            // soustraire de l'argent du joueur le cout de la quantité
            // achetée et mettre à jour la quantité de product
            // uN = u1 ((1-r^n)/(1-r))
            double argent = product.getCout()
                    * ((1 - Math.pow(product.getCroissance(), qtchange)) / (1 - product.getCroissance()));
            double argent2 = world.getMoney() - argent;

            world.setMoney(argent2);

            int nbproduit = product.getQuantite() + qtchange;
            product.setQuantite(nbproduit);

        } else {
            // initialiser product.timeleft à product.vitesse
            // pour lancer la production}
            // sauvegarder les changements du monde
            long tl = product.getVitesse();
            product.setTimeleft(tl);
        }
        saveWordlToXml(world, username);
        System.out.println(username);
        return true;
    }

    public ProductType findProductById(World world, int id) {
        return world.getProducts().getProduct().get(id);
    }
}
