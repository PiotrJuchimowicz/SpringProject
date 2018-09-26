package comcompany.app.base.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        //send mail to admin with logs containing statistics(using AOP) ... - TODO
        return "error";
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
