package comcompany.app.base.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/loginForm",method = RequestMethod.GET)
    public String login() {
        return "loginForm";
    }


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        return "boss/index";
    }

    @RequestMapping(value = "/authenticateUser",method = RequestMethod.POST)
    public void method()
    {
        System.out.println("HI");
    }
}
