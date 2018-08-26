package comcompany.app.base.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/loginForm",method = RequestMethod.GET)
    public String login() {
        return "loginForm";
    }

    @RequestMapping(value = "/loginProcessing",method = RequestMethod.POST)
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println(username);
        System.out.println(password);

        return "boss/index";
    }
}
