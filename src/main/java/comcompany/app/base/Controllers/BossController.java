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
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

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

    //returns initial boss view
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "boss/index";

    }




    //TODO
    //mozna zwracac Stringiem widok i pobierać model do którego dodajemy
    //dane parametry do widoku
    @RequestMapping(value = "/employee/getEmployeeForm", method = RequestMethod.GET)
    public String getEmployeeForm(Model model) {

        model.addAttribute("employee",new Employee());

        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        Object[] filteredPositions =  Arrays.stream(positions).filter(position -> (position != Position.ADMIN && position != Position.BOSS)).toArray();


        //sending positions(without boss and admin) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/addEmployeeForm";
    }


    @RequestMapping(value = "employee/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        //walidacja czy dodalo czy juz taki byl(powinien rzucic exception z glebszej wartstwy)

        employeeService.create(employee);

        return "boss/index";

    }

    //mozna tez po prostu zwrocic obiekt klasy ModelAndView
    //i w nim zawrzec modele i widok
    @RequestMapping("/showAllDevelopers")
    public ModelAndView showAllDevelopers()
    {
        List<Employee> allDevelopers= employeeService.findEmployeesByPosition(Position.DEVELOPER);
        ModelAndView mav= new ModelAndView("boss/showAllDevelopers");
        mav.addObject("developers",allDevelopers);

        return  mav;


    }
}
