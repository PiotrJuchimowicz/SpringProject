package comcompany.app.base.Controllers;

import comcompany.app.base.Exceptions.IncorrectFormDataException;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.*;

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
       /* //!!!!!!!!!!!!
        String password="1234";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash= encoder.encode(password);
        System.out.println(passwordHash);
        Employee employee = new Employee("Piotr","Juchimowicz","piotrjuchimowicz@gmail.com","Bialystok",true,2500, Position.BOSS);
        employee.setPasswordHash(passwordHash);
        employeeService.create(employee);
        employeeService.create(employee);
        //!!!!!!!!!!!!!!*/
        model.addAttribute("employee", new Employee());
        Object[] filteredPositions = getPositionsExceptBoss();
        //sending positions(without boss  - because boss is only one) to view in case of iterate them in radio -form
        model.addAttribute("positions", filteredPositions);
        return "boss/employee/addForm";
    }

    @RequestMapping(value = "employee/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("employee") Employee employee, Model model) {
        //validation later
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        employee.setPassword(encoder.encode("1234"));
        employeeService.create(employee);
        return "boss/employee/menu";
    }

    //returns menu with options how to search for employees
    @RequestMapping(value = "employee/searchingCriteria", method = RequestMethod.GET)
    public String showSearchingCriteria() {
        return "boss/employee/searchingCriteria";
    }

    //also ModelAndView can be thrown - it contains data and view
    @RequestMapping(value = "/employee/showAll", method = RequestMethod.GET)
    public ModelAndView showAll() {
        List<Employee> allEmployees = employeeService.getAll();
        List<String> fieldsListToString = getFieldsToView();
        ModelAndView mav = new ModelAndView("boss/employee/showAll");
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
    @RequestMapping(value = "employee/showFiltered", method = RequestMethod.POST)
    public String showFiltered(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "surname", required = false) String surname,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "city", required = false) String city,
                               @RequestParam(value = "lower limit", required = false) Double lowerLimit,
                               @RequestParam(value = "upper limit", required = false) Double upperLimit,
                               @RequestParam(value = "position", required = false) Position position,
                               Model model) {
        List<Employee> mergedList;
        try {
            mergedList = getEmployeesWithSelectedAttributes(name, surname, city, lowerLimit, upperLimit, position, email);
        } catch (IncorrectFormDataException e) {
            return "/boss/employee/invalidFormData";
        }
        model.addAttribute("employees", mergedList);
        List<String> fieldsListToString = getFieldsToView();
        model.addAttribute("fields", fieldsListToString);
        //sending to view how they've been filtered so after update/delete/sort same as filtered employees will be returned
        Map<String, String> requirementWrappers = getRequirementWrappers(name, surname, email, city, lowerLimit, upperLimit, position);
        model.addAttribute("requirementWrappers", requirementWrappers);
        return "boss/employee/showFiltered";
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
    public String updateForm(@ModelAttribute("employee") Employee employee, Model model) {
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        employeeService.update(employee);
        return "redirect:/boss/index";
    }

    @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        employeeService.delete(id);
       return "/boss/index";
    }

    @RequestMapping(value = "/employee/sortBy", method = RequestMethod.GET)
    public String getSorted(@RequestParam(name = "filtered", required = false) boolean filtered,
                            @RequestParam("sortBy") String sortBy,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            @RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "lowerLimit", required = false) Double lowerLimit,
                            @RequestParam(value = "upperLimit", required = false) Double upperLimit,
                            @RequestParam(value = "position", required = false) Position position, Model model) {
        if (filtered) {
            {
                List<String> fieldsListToString = getFieldsToView();
                model.addAttribute("fields", fieldsListToString);
                List<Employee> mergedListContainingEmployeesMeetingCriteria;
                try {
                    mergedListContainingEmployeesMeetingCriteria = getEmployeesWithSelectedAttributes(name, surname, city, lowerLimit, upperLimit, position, email);
                } catch (IncorrectFormDataException e) {
                    return "/boss/employee/invalidFormData";
                }
                sortByAttribute(mergedListContainingEmployeesMeetingCriteria, sortBy);
                model.addAttribute("employees", mergedListContainingEmployeesMeetingCriteria);
                //sending to view how they've been filtered so after update/delete/sort proper employees will be returned
                Map<String, String> requirementWrappers = new LinkedHashMap<>();
                String secondConstructorArgument;
                requirementWrappers.put("name", name);
                requirementWrappers.put("surname", surname);
                requirementWrappers.put("email", email);
                requirementWrappers.put("city", city);
                if (lowerLimit != null)
                    secondConstructorArgument = Double.toString(lowerLimit);
                else
                    secondConstructorArgument = "";
                requirementWrappers.put("lowerLimit", secondConstructorArgument);
                if (upperLimit != null)
                    secondConstructorArgument = Double.toString(upperLimit);
                else
                    secondConstructorArgument = "";
                requirementWrappers.put("upperLimit", secondConstructorArgument);
                if (position != null)
                    secondConstructorArgument = position.toString();
                else
                    secondConstructorArgument = "";
                requirementWrappers.put("position", secondConstructorArgument);
                model.addAttribute("requirementWrappers", requirementWrappers);
            }
            return "/boss/employee/showFiltered";
        } else {
            List<String> fieldsListToString = getFieldsToView();
            model.addAttribute("fields", fieldsListToString);
            List<Employee> allEmployees = employeeService.getAll();
            sortByAttribute(allEmployees, sortBy);
            model.addAttribute("employees", allEmployees);
            return "/boss/employee/showAll";
        }
    }

    private Map<String, String> getRequirementWrappers(String name, String surname,
                                                       String email, String city, Double lowerLimit,
                                                       Double upperLimit, Position position) {
        Map<String, String> requirementWrappers = new LinkedHashMap<>();
        String secondConstructorArgument;
        requirementWrappers.put("name", name);
        requirementWrappers.put("surname", surname);
        requirementWrappers.put("email", email);
        requirementWrappers.put("city", city);
        if (lowerLimit != null)
            secondConstructorArgument = Double.toString(lowerLimit);
        else
            secondConstructorArgument = "";
        requirementWrappers.put("lowerLimit", secondConstructorArgument);
        if (upperLimit != null)
            secondConstructorArgument = Double.toString(upperLimit);
        else
            secondConstructorArgument = "";
        requirementWrappers.put("upperLimit", secondConstructorArgument);
        if (position != null)
            secondConstructorArgument = position.toString();
        else
            secondConstructorArgument = "";
        requirementWrappers.put("position", secondConstructorArgument);

        return requirementWrappers;
    }

    private List<Employee> getEmployeesWithSelectedAttributes(String name, String surname,
                                                              String city, Double lowerLimit,
                                                              Double upperLimit, Position position,
                                                              String email) {
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
                throw new IncorrectFormDataException(e.getMessage());
            }
        }
        if (position != null) {
            List<Employee> employeesByPosition = employeeService.findEmployeesByPosition(position);
            listOfQueriesResults.add(employeesByPosition);
        }
        if (!email.equals("")) {
            Employee employee = employeeService.findEmployeesByEmail(email);
            List<Employee> employeesByEmail=new LinkedList<>();
            employeesByEmail.add(employee);
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
        return mergedList;
    }

    private void sortByAttribute(List<Employee> employeesToSort, String field) {
        if (field.equals("Name"))
            employeesToSort.sort(Comparator.comparing(Employee::getName));
        else if (field.equals("Surname"))
            employeesToSort.sort(Comparator.comparing(Employee::getSurname));
        else if (field.equals("Email"))
            employeesToSort.sort(Comparator.comparing(Employee::getEmail));
        else if (field.equals("City"))
            employeesToSort.sort(Comparator.comparing(Employee::getCity));
        else if (field.equals("Salary"))
            employeesToSort.sort(Comparator.comparing(Employee::getSalary));
        else if (field.equals("Position"))
            employeesToSort.sort(Comparator.comparing(Employee::getPosition));
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

    private Object[] getPositionsExceptBoss() {
        //getting  possible possitions
        Position[] positions = Position.values();
        //removing ADMIN and BOSS - they shouldnt be able to add in form by BOSS
        return Arrays.stream(positions).filter(position -> (position != Position.BOSS)).toArray();
    }
}
