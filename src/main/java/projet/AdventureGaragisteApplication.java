package projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventureGaragisteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdventureGaragisteApplication.class, args);

        Services s = new Services();
        World world = s.getWorld();
        s.saveWordlToXml(world);

    }

}
