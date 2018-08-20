package comcompany.app.base.Controllers;


import comcompany.app.base.Exceptions.IncorrectFormDataException;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.EmployeeService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/boss")
public class BossEmployeeMangementController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //returns view for Employees mangement
    @RequestMapping("/employee/management")
    public String management() {
        return "boss/employee/management";

    }

    //gets model , sends data to view and returns that view(model is sending behind the scenes)
    @RequestMapping(value = "/employee/getEmployeeForm", method = RequestMethod.GET)
    public String getForm(Model model) {

        model.addAttribute("employee", new Employee());

        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        Object[] filteredPositions = Arrays.stream(positions).filter(position -> (position != Position.BOSS)).toArray();


        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/addForm";
    }


    @RequestMapping(value = "employee/addEmployee", method = RequestMethod.POST)
    public String add(@ModelAttribute("employee") Employee employee) {
        //validation later

        employeeService.create(employee);

        return "boss/index";

    }

    //returns menu with options how to search for employees
    @RequestMapping(value = "employee/showSearchingCriteria", method = RequestMethod.GET)
    public String showSearchingCriteria() {
        return "boss/employee/showSearchingCriteria";
    }


    //also ModelAndView can be thrown - it contains data and view
    @RequestMapping(value = "/employee/showAll", method = RequestMethod.GET)
    public ModelAndView showSelected() {
        List<Employee> allEmployees = employeeService.getAll();
        List<String> fieldsListToString = getFieldsToView();

        //TODO UNABLE TO BIND TO LIST !

        ModelAndView mav = new ModelAndView("boss/employee/showSelected");
        mav.addObject("employees", allEmployees);
        mav.addObject("fields", fieldsListToString);

        return mav;


    }



    @RequestMapping(value = "/employee/showByNameAndSurnameForm",method = RequestMethod.GET)
    public String enterNameAndSurname(Model model)
    {
        List<String> fields=new LinkedList<>();
        fields.add("name");
        fields.add("surname");
        model.addAttribute("fields",fields);





        return "boss/employee/enterSearchingCriteria";

    }


    @RequestMapping(value ="/employee/showByNameAndSurname",method = RequestMethod.POST)
    public String showByNameAndSurname(@RequestParam("name") String name,@RequestParam("surname") String surname,Model model)
    {

        List<Employee> result=employeeService.findEmployeesByNameAndSurname(name,surname);
        model.addAttribute("employees",result);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("fields",fieldsListToString);




        return "boss/employee/showSelected";

    }


    @RequestMapping(value = "employee/showByCityForm",method = RequestMethod.GET)
    public String enterCity(Model model)
    {
        List<String> fields=new LinkedList<>();
        fields.add("city");
        model.addAttribute("fields",fields);

        return "boss/employee/enterSearchingCriteria";


    }

    @RequestMapping(value = "/employee/showByCity",method = RequestMethod.POST)
    public String showByCity(@RequestParam("city") String city,Model model)
    {
        List<Employee> result=employeeService.findEmployeesByCity(city);
        model.addAttribute("employees",result);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("fields",fieldsListToString);


        return "/boss/employee/showSelected";
    }

    @RequestMapping(value = "employee/showBySalaryForm",method = RequestMethod.GET)
    public String enterSalary(Model model)
    {
        List<String> fields=new LinkedList<>();
        fields.add("lower limit");
        fields.add("upper limit");
        model.addAttribute("fields",fields);

        return "boss/employee/enterSearchingCriteria";

    }

    @RequestMapping(value = "/employee/showBySalary",method = RequestMethod.POST)
    public String showByCity(@RequestParam("lower limit") double lowerLimit,@RequestParam("upper limit") double upperLimit, Model model)
    {
        List<Employee> result=null;
        try {
            result = employeeService.findEmployeesBySalaryBetween(lowerLimit, upperLimit);
        }
        catch (IncorrectFormDataException e)
        {
            //TODO return personalised invalid form page
            return "/boss/employee/error";
        }
        model.addAttribute("employees",result);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("fields",fieldsListToString);


        return "/boss/employee/showSelected";
    }

    @RequestMapping(value = "employee/showByPositionForm",method = RequestMethod.GET)
    public String enterPosition(Model model)
    {
        List<String> fields=new LinkedList<>();
        fields.add("position");

        model.addAttribute("fields",fields);

        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        Object[] filteredPositions = Arrays.stream(positions).filter(position -> (position != Position.BOSS)).toArray();


        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/enterSearchingCriteria";

    }

    @RequestMapping(value = "/employee/showByPosition",method = RequestMethod.POST)
    public String showByCity(@RequestParam("position") Position position, Model model)
    {
        List<Employee> result=employeeService.findEmployeesByPosition(position);
        model.addAttribute("employees",result);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("fields",fieldsListToString);


        return "/boss/employee/showSelected";
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
                fieldsListToString.add(fieldToString);
            }


        }

        return fieldsListToString;
    }
}
