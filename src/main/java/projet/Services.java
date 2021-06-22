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
        this.calcMoney(world);
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
        world = this.calcMoney(world);

        // trouver dans ce monde, le manager équivalent à celui passé
        // en paramètre
        int managerIndex = 0;
        for (PallierType m : world.managers.pallier) {
            if (newmanager.getName().equals(m.getName())) {
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
        ProductType product = world.products.getProduct().get(manager.getIdcible() - 1);
        if (product == null) {
            return false;
        } else {
            product.setManagerUnlocked(true);
        }

        // soustraire de l'argent du joueur le cout du manager
        world.money -= manager.getSeuil();

        // sauvegarder les changements au monde
        this.saveWordlToXml(world, username);
        return true;
    }

    public Boolean updateUpgrade(String username, PallierType newupgrade) {

        // aller chercher le monde qui correspond au joueur
        World world = this.getWorld(username);
        world = this.calcMoney(world);

        // trouver dans ce monde, le manager équivalent à celui passé
        // en paramètre
        int upgradeIndex = 0;
        for (PallierType u : world.upgrades.pallier) {
            if (newupgrade.getName().equals(u.getName())) {
                upgradeIndex = world.upgrades.pallier.indexOf(u);
            }
        }

        PallierType upgrade = world.upgrades.pallier.get(upgradeIndex);
        if (upgrade == null) {
            return false;
        } else {
            upgrade.setUnlocked(true);
        }

        // trouver le produit correspondant au manager
        ProductType product = world.products.getProduct().get(upgrade.getIdcible() - 1);
        if (product == null) {
            return false;
        } else {
            switch (upgrade.getIdcible()) {
            case -1:
                world.angelbonus += upgrade.getRatio();
                break;
            case 0:
                for (ProductType p : world.products.getProduct()) {
                    p.vitesse = (int) (product.getVitesse() / upgrade.getRatio());
                }
                break;
            default:
                product.vitesse = (int) (product.getVitesse() / upgrade.getRatio());
                break;
            }
        }

        // soustraire de l'argent du joueur le cout du manager
        world.money -= upgrade.getSeuil();

        // sauvegarder les changements au monde
        this.saveWordlToXml(world, username);
        return true;
    }

    public Boolean updateAngelUpgrade(String username, PallierType newangelupgrade) {

        // aller chercher le monde qui correspond au joueur
        World world = this.getWorld(username);
        world = this.calcMoney(world);

        // trouver dans ce monde, le manager équivalent à celui passé
        // en paramètre
        int angelUpgradeIndex = 0;
        for (PallierType au : world.angelupgrades.pallier) {
            if (newangelupgrade.getName().equals(au.getName())) {
                angelUpgradeIndex = world.angelupgrades.pallier.indexOf(au);
            }
        }

        PallierType angelUpgrade = world.angelupgrades.pallier.get(angelUpgradeIndex);
        if (angelUpgrade == null) {
            return false;
        } else {
            angelUpgrade.setUnlocked(true);
        }

        switch (angelUpgrade.getIdcible()) {
        case -1:
            world.angelbonus += angelUpgrade.getRatio();
            break;
        case 0:
            for (ProductType p : world.products.getProduct()) {
                p.revenu = (int) (p.getRevenu() * angelUpgrade.getRatio());
            }
            break;
        default:
            ProductType product = world.products.getProduct().get(angelUpgrade.getIdcible() - 1);
            if (product == null) {
                return false;
            }
            if (angelUpgrade.getTyperatio() == TyperatioType.GAIN) {
                product.revenu = (int) (product.getRevenu() * angelUpgrade.getRatio());
            }
            if (angelUpgrade.getTyperatio() == TyperatioType.QUANTITE) {
                product.quantite += angelUpgrade.getRatio();
            }
            break;
        }
        // soustraire de l'argent du joueur le cout du manager
        world.activeangels -= angelUpgrade.getSeuil();

        // sauvegarder les changements au monde
        this.saveWordlToXml(world, username);
        return true;
    }

    public World calcMoney(World world) {
        long currentTime = System.currentTimeMillis();
        long spentTime = currentTime - world.getLastupdate();
        world.setLastupdate(currentTime);
        for (ProductType p : world.products.product) {
            if (p.isManagerUnlocked()) {
                double earnedMoney = Math.floor(spentTime / p.getVitesse()) * (p.getQuantite() * p.getRevenu()
                        * (1 + (world.getActiveangels() * world.getAngelbonus()) / 100));
                world.money += earnedMoney;
                world.score += earnedMoney;
                p.timeleft = spentTime % p.getVitesse();
            } else {
                if (p.timeleft != 0) {
                    if (spentTime < p.getTimeleft()) {
                        p.setTimeleft(p.getTimeleft() - spentTime);
                    } else {
                        p.setTimeleft(0);
                        double earnedMoney = p.getQuantite() * p.getRevenu()
                                * (1 + (world.getActiveangels() * world.getAngelbonus()) / 100);
                        world.money += earnedMoney;
                        world.score += earnedMoney;
                    }
                }
            }
        }
        return world;
    }

    public Boolean updateProduct(String username, ProductType newproduct) {
        World world = getWorld(username);

        world = this.calcMoney(world);

        ProductType product = findProductById(world, newproduct.getId());

        if (product == null) {
            return false;
        }

        int qtchange = newproduct.getQuantite() - product.getQuantite();
        if (qtchange > 0) {
            // soustraire de l'argent du joueur le cout de la quantité
            // achetée et mettre à jour la quantité de product
            // uN = u1 ((1-r^n)/(1-r))
            double argent = product.getCout() * Math.pow(product.getCroissance(), qtchange);
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
        return world.getProducts().getProduct().get(id - 1);
    }

    public void resetWorld(String username) {
        World world = getWorld(username);
        world = this.calcMoney(world);
        double score = world.getScore();
        double totalangels = world.getTotalangels();
        double activeangel = world.getActiveangels();
        double claimedAngel = Math.floor(150 * Math.sqrt(score / Math.pow(10, 15)) - totalangels);
        InputStream input = null;
        input = getClass().getClassLoader().getResourceAsStream("world.xml");
        try {
            JAXBContext cont = JAXBContext.newInstance(World.class);
            Unmarshaller u = cont.createUnmarshaller();
            World newWorld = (World) u.unmarshal(input);
            newWorld.setScore(score);
            newWorld.setActiveangels(activeangel + claimedAngel);
            newWorld.setTotalangels(totalangels + claimedAngel);
            saveWordlToXml(newWorld, username);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
