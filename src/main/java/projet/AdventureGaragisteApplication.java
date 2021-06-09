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
import javax.xml.bind.JAXBContext;


@SpringBootApplication
public class AdventureGaragisteApplication {

 	public static void main(String[] args) {
            SpringApplication.run(AdventureGaragisteApplication.class, args);
            
            Services s = new Services();
            s.readWorldFromXml();
            
            
		
                
            
	}

    
}
