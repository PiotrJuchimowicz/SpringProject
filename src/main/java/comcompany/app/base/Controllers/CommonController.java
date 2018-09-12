package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//TODO move refactored profiles there
@Controller
public class CommonController {
    private EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    public void setEmployeeService(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    @RequestMapping(value = "/showProfile",method = RequestMethod.GET)
    public String profile(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email= user.getUsername();
        Employee employee = employeeService.findEmployeesByEmail(email);
        if(employee==null)
        {
            log.warn("Unable to load profile. You may be super admin.");
            log.warn("Redirection to homepage");
            return "redirect:homepage.";
        }
        model.addAttribute("employee",employee);
        return "profile";
    }
    @RequestMapping(value = "/deleteProfile",method = RequestMethod.GET)
    public String deleteProfile(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response)
    {
        employeeService.delete(employeeService.findEmployeesByEmail(email).getId());
        log.warn("Deleted user identified by: " + email);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/loginForm";
    }
    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateForm(@ModelAttribute("employee") Employee employee) {
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        employeeService.update(employee);
        return "redirect:/showProfile";
    }

    @RequestMapping(value = "/changePasswordForm",method = RequestMethod.GET)
    public String changePasswordForm(@RequestParam("email")String email, Model model)
    {
        model.addAttribute("email",email);
        return "changePasswordForm";
    }
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public String changePassword(@RequestParam("email")String email,
                                 @RequestParam("passwordA")String passwordA,
                                 @RequestParam("passwordB")String passwordB)
    {
        if (!passwordA.equals(passwordB))
            return"incorrectPasswords";
        Employee employee=employeeService.findEmployeesByEmail(email);
        employee.setPassword(new BCryptPasswordEncoder().encode(passwordA));
        employeeService.update(employee);
        log.warn("User identified by " + email + " has changed password");
        return "redirect:/homepage";
    }
}
