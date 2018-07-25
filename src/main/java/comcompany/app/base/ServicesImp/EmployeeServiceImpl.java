package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee> implements EmployeeService {

    @Autowired
    public void setRepository(GenericRepository<Employee> employeeRepository)
    {
        this.setGenericRepository(employeeRepository);
    }
}
