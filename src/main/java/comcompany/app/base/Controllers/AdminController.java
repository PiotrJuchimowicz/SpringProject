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
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/boss/menu")
    public String management() {
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
        //mail to boss
        boss.setPassword(new BCryptPasswordEncoder().encode(boss.getPassword()));
        employeeService.create(boss);
        return "admin/boss/menu";
    }

    @RequestMapping(value = "/boss/searchingCriteria", method = RequestMethod.GET)
    public String showSearchingCriteria() {
        return "admin/boss/searchingCriteria";
    }

    @RequestMapping(value = "/boss/showAll", method = RequestMethod.GET)
    public String showAll(Model model) {
        List<Employee> allEmployees = employeeService.findEmployeesByPosition(Position.BOSS);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("employees", allEmployees);
        model.addAttribute("fields", fieldsListToString);
        return "/admin/boss/showAll";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        Employee admin = employeeService.findEmployeesByEmail(email);
        model.addAttribute("admin", admin);
        return "admin/index";
    }

    private List<String> getFieldsToView() {
        Field[] fields = employeeService.getAllFields(Employee.class);
        List<String> fieldsListToString = new LinkedList<>();
        for (Field field : fields) {
            if (!((field.getName().equals("department")) || field.getName().equals("tasks") || field.getName().equals("id") || field.getName().equals("sentMessages") || field.getName().equals("receivedMessages"))) {
                String fieldToString = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1).toLowerCase();
                //conversion from Email to E-mail
                if (fieldToString.equals("Email"))
                    fieldToString = "E-mail";
                if(fieldToString.equals("Password")||fieldToString.equals("Enabled"))
                    continue;
                fieldsListToString.add(fieldToString);
            }
        }
        return fieldsListToString;
    }
}
