package comcompany.app.base.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/boss")
public class BossIndexController {

    //returns initial boss view
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "boss/index";

    }

}
