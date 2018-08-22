package comcompany.app.base.Controllers;


import comcompany.app.base.Exceptions.IncorrectFormDataException;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/boss")
public class BossEmployeeMangementController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //returns view for Employees mangement
    @RequestMapping("/employee/menu")
    public String management() {
        return "boss/employee/menu";

    }

    //gets model , sends data to view and returns that view(model is sending behind the scenes)
    @RequestMapping(value = "/employee/addForm", method = RequestMethod.GET)
    public String getForm(Model model) {

        model.addAttribute("employee", new Employee());
        Object[] filteredPositions = getPositionsExceptBoss();

        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/addForm";
    }

    @RequestMapping(value = "employee/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("employee") Employee employee) {
        //validation later

        employeeService.create(employee);

        return "boss/index";

    }

    //returns menu with options how to search for employees
    @RequestMapping(value = "employee/searchingCriteria", method = RequestMethod.GET)
    public String showSearchingCriteria() {
        return "boss/employee/searchingCriteria";
    }

    //also ModelAndView can be thrown - it contains data and view
    @RequestMapping(value = "/employee/showAll", method = RequestMethod.GET)
    public ModelAndView showSelected() {
        List<Employee> allEmployees = employeeService.getAll();
        List<String> fieldsListToString = getFieldsToView();


        ModelAndView mav = new ModelAndView("boss/employee/showSelected");
        mav.addObject("employees", allEmployees);
        mav.addObject("fields", fieldsListToString);

        return mav;
    }

    @RequestMapping(value = "/employee/searchForm", method = RequestMethod.GET)
    public String getSearchForm(Model model) {

        Object[] filteredPositions = getPositionsExceptBoss();

        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/searchForm";
    }


    //gets parameters from searchForm and returns view with merged lists contains common elements - employees meeting used searching criteria
    @RequestMapping(value = "employee/showSelected", method = RequestMethod.POST)
    public String getSelected(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "surname", required = false) String surname,
                              @RequestParam(value = "city", required = false) String city,
                              @RequestParam(value = "lower limit", required = false) Double lowerLimit,
                              @RequestParam(value = "upper limit", required = false) Double upperLimit,
                              @RequestParam(value = "position", required = false) Position position,
                              @RequestParam(value = "email", required = false) String email,
                              Model model) {

        //contains lists of querry results
        List<List<Employee>> listOfQueriesResults = new LinkedList<>();

        if (!name.equals("")) {
            List<Employee> employeesByName = employeeService.findEmployeesByName(name);
            listOfQueriesResults.add(employeesByName);
        }
        if (!surname.equals("")) {
            List<Employee> employeesBySurname = employeeService.findEmployeesBySurname(surname);
            listOfQueriesResults.add(employeesBySurname);

        }
        if (!city.equals("")) {
            List<Employee> employeesByCity = employeeService.findEmployeesByCity(city);
            listOfQueriesResults.add(employeesByCity);
        }
        if (lowerLimit != null && upperLimit != null) {
            try {
                List<Employee> employeesBySalaryBetween = employeeService.findEmployeesBySalaryBetween(lowerLimit, upperLimit);
                listOfQueriesResults.add(employeesBySalaryBetween);
            } catch (IncorrectFormDataException e) {
                return "boss/employee/invalidFormData";
            }
        }
        if (position != null) {
            List<Employee> employeesByPosition = employeeService.findEmployeesByPosition(position);
            listOfQueriesResults.add(employeesByPosition);
        }
        if (!email.equals("")) {
            List<Employee> employeesByEmail = employeeService.findEmployeesByEmail(email);
            listOfQueriesResults.add(employeesByEmail);
        }

        List<Employee> mergedList = new LinkedList<>();

        boolean isFirstLoop = true;

        for (List<Employee> list : listOfQueriesResults) {
            if (isFirstLoop) {
                mergedList.addAll(list);
                isFirstLoop = false;
            } else {
                mergedList.retainAll(list);
            }
        }

        model.addAttribute("employees", mergedList);

        return "boss/employee/showSelected";
    }

    @RequestMapping(value = "/employee/updateForm", method = RequestMethod.GET)
    public String updateForm(@RequestParam("id") Long id, Model model) {


        Employee employee = employeeService.read(id);
        model.addAttribute("employee", employee);

        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        Object[] filteredPositions = Arrays.stream(positions).filter(position -> (position != Position.BOSS)).toArray();

        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);

        return "boss/employee/updateForm";

    }

    @RequestMapping(value = "employee/update", method = RequestMethod.POST)
    public String updateForm(@ModelAttribute("employee") Employee employee) {


        employeeService.update(employee);

        return "redirect:/boss/employee/showAll";

    }

    @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        employeeService.delete(id);

        return "redirect:/boss/employee/showAll";

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

    private Object[] getPositionsExceptBoss(){
        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        return Arrays.stream(positions).filter(position -> (position != Position.BOSS)).toArray();
    }

}
