package comcompany.app.base.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class StartController {
    public final Logger log = LoggerFactory.getLogger(StartController.class);

    @RequestMapping(value = "/")
    public String homepageRedirection() {
        return "redirect:/homepage";
    }

    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    public String login() {
        return "loginForm";
    }

    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String index() {
        log.info("User logged: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO make more smart
        String authority =userDetails.getAuthorities().toArray()[0].toString();
        System.out.println(authority);
       if (authority.equals("ADMIN"))
            return "redirect:admin/index";
        if (authority.equals("BOSS"))
            return "redirect:boss/index";
        if (authority.equals("EMPLOYEE"))
            return "redirect:employee/index";
        else return "error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/loginForm";
    }

    @RequestMapping(value = "/badLogin", method = RequestMethod.GET)
    public String badLogin() {
        return "/badLogin";
    }
}
