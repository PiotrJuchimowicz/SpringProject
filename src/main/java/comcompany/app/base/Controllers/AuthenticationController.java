package comcompany.app.base.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    public String authenticate(@RequestParam("email") String email, @RequestParam("password") String passwrd) {
        System.out.println(email);
        System.out.println(passwrd);

        return "boss/index";
    }
}
