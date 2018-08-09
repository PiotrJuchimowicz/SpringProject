package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//walidacja formularza
//do kazdej strony z wyborem kazdy aktor bedzie sie logowal(Spring Security)
@Controller
@RequestMapping("/boss")
public class BossController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/selection", method = RequestMethod.GET)
    public String selection() {
        return "boss/selection";

    }


    @RequestMapping(value = "/getEmployeeForm", method = RequestMethod.GET)
    public String getEmployeeForm(Model model) {

        model.addAttribute("employee", new Employee());

        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        Object[] filteredPositions =  Arrays.stream(positions).filter(position -> (position != Position.ADMIN && position != Position.BOSS)).toArray();


        //sending positions(without boss and admin) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/addEmployeeForm";
    }


    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        //walidacja czy dodalo czy juz taki byl(powinien rzucic exception z glebszej wartstwy)
        System.out.println(employee);
        employeeService.create(employee);

        return "boss/selection";

    }
}
