package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Services.DepartmentService;
import comcompany.app.base.Services.EmployeeService;
import comcompany.app.base.ServicesImp.DepartmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {


    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public void setDepartmentService(DepartmentServiceImpl departmentService,EmployeeService employeeService)
    {
        this.departmentService=departmentService;
        this.employeeService=employeeService;
    }





    @RequestMapping("/")
    public String getIndex(Model model)
    {
       /* Employee employee = new Employee("a","b",Position.MANAGER,1);
        Employee employee1 = new Employee("b","b",Position.PROGRAMMER,2);
        Employee employee2 = new Employee("c","c",Position.TESTER,3);

        employeeService.create(employee);
        employeeService.create(employee1);
        employeeService.create(employee2);*/

       Department department = new Department("a","a");
       Department department1 = new Department("b","b");
       Department department2 = new Department("c","c");

       departmentService.create(department);
       departmentService.create(department1);
       departmentService.create(department2);
            return "index";
    }

    @RequestMapping("/emplQuerryResult")
    public String emplQuerryResult(Model model)
    {
        List<Department> list = departmentService.findByLocation("a");
        System.out.println(list);

        return "index";

    }
}
