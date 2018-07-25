package comcompany.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//2 tabele - employee i task
//employee za pomoca plain JPA a task za pomoca Spring Data
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
//TODO
//Poczytaj o tym jak obslugiwac wyjatki w springu MVC
//Ogarnij ten sprytny podzial na warstwy z uzyciem generykow
//Zaimplementuj SLF4J loggera z jawna implementacja logback.xml
//Wlasne wyjatki zrob


