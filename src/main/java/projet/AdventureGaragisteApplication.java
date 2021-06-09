package projet;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@SpringBootApplication
public class AdventureGaragisteApplication {

 	public static void main(String[] args) {
            SpringApplication.run(AdventureGaragisteApplication.class, args);
            
            try {JAXBContext cont = JAXBContext.newInstance(World.class);
            Unmarshaller u = cont.createUnmarshaller();
            World world = (World) u.unmarshal(new File("src/main/resources/world.xml"));
            
            System.out.print(world.getName());
            
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
            
		
                
            
	}

    
}
