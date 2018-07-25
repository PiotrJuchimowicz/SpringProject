package comcompany.app.base.Controllers;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Services.DepartmentService;
import comcompany.app.base.ServicesImp.DepartmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    private DepartmentService departmentService;
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public void setDepartmentService(DepartmentServiceImpl departmentService)
    {
        this.departmentService=departmentService;
    }





    @RequestMapping("/")
    public String getIndex(Model model)
    {
        long id=1;
        Department department = departmentService.read(id);
        department.setLocation("newerrrrr location");
        department=departmentService.update(department);
        System.out.println(department);
        model.addAttribute("department",department);

        return "index";
    }
}
