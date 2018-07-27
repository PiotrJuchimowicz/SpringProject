package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee> implements EmployeeService {

    @Autowired
    public void setRepository(GenericRepository<Employee> employeeRepository)
    {
        this.setGenericRepository(employeeRepository);
    }



    @Override
    public List<Employee> findEmployeesByDepartment(Department department) {
        return null;
    }

    @Override
    public List<Employee> findEmployeesByPosition(Position position) {
        return null;
    }

    @Override
    public List<Employee> findEmployeesBySalaryBetween(double lowerLimit, double upperLimit) {
        return null;
    }

    @Override
    public List<Employee> findEmployeesWorkingOnTask(Task task) {
        return null;
    }
}
