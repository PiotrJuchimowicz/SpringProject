package comcompany.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
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
//actors : boss,admin,developer,tester,manager
//form validation  with spring
//criteria api
//captcha
//later : pdf with statistic about company(count of developers,salary ....)
//import bootstrap files from one file