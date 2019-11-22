package fi.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TehtavalistaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(TehtavalistaApplication.class, args);
    }

    //Tämä ja yllä oleva app extends SBSI tarvitaan, jotta voidaan deployata ulkoiselle serverille (Tomcat)
    //lisäksi pom.xml package war lisätty ennen riippuvuuksia (oletuksena jar)
    //tee war terminaalissa mvn clean ja mvn install -> nimeä uudelleen TehtavalistaApplication
    //ja kopioi war tomcatin webapps kansioon
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(TehtavalistaApplication.class);
    }

}
