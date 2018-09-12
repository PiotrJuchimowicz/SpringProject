package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private EmployeeService employeeService;

    @Autowired
    public void  setEmployeeService(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    @RequestMapping(value = "/boss/menu")
    public String management()
    {
        return "admin/boss/menu";
    }

    //gets model , sends data to view and returns that view(model is sending behind the scenes)
    @RequestMapping(value = "/boss/addForm", method = RequestMethod.GET)
    public String getForm(Model model) {
        //mail to boss and boss must type password(username <==> email)
        model.addAttribute("boss", new Employee());
        return "admin/boss/addForm";
    }

    @RequestMapping(value = "boss/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("boss") Employee boss) {
        //TODO validation -server and client side
        boss.setPosition(Position.BOSS);
        //temporary before fully implement password feature
        boss.setPassword(new BCryptPasswordEncoder().encode("1234"));
        employeeService.create(boss);
        return "admin/boss/menu";
    }
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email= user.getUsername();
        Employee admin = employeeService.findEmployeesByEmail(email);
        model.addAttribute("admin",admin);
        return "admin/index";
    }
}
