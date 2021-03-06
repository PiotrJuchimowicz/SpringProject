package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/boss")
public class BossIndexController {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }
    //returns initial boss view
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email= user.getUsername();
        Employee boss = employeeService.findEmployeesByEmail(email);
        model.addAttribute("boss",boss);
        return "boss/index";
    }

}
