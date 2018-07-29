package comcompany.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
//TODO
//Good practies with exceptions and Spring MVC
//Layer division
//SLF4J with logback need to be implemented
//Own exceptions
//registration with mail support
//password-hash
//only boss can assign tasks and watch  employees statistics



