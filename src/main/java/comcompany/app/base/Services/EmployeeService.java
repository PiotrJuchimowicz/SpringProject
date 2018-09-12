package comcompany.app.base.Services;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;

import java.util.List;


public interface EmployeeService extends GenericService<Employee> {
    List<Employee> findEmployeesByDepartment(Department department);

    List<Employee> findEmployeesByPosition(Position position);

    List<Employee> findEmployeesBySalaryBetween(Double lowerLimit, Double upperLimit);

    List<Employee> findEmployeesWorkingOnTask(Task task);

    List<Employee> findEmployeesByCity(String city);

    List<Employee> findEmployeesByName(String name);

    List<Employee> findEmployeesBySurname(String surname);

    //TODO make email unique
    Employee findEmployeesByEmail(String email);
}
