package comcompany.app.base.Services;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService extends GenericService<Employee> {
    List<Employee> findEmployeesByDepartment(Department department);

    List<Employee> findEmployeesByPosition(Position position);

    List<Employee> findEmployeesBySalaryBetween(double lowerLimit, double upperLimit);

    List<Employee> findEmployeesWorkingOnTask(Task task);
}
