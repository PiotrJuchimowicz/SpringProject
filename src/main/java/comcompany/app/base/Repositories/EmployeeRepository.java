package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//Methods return empty collections or collections of objects meeting the criteria
@Repository
public interface EmployeeRepository extends GenericRepository<Employee> {
    List<Employee> findEmployeesByDepartment(Department department);

    @Query("select employee From Employee employee  where :task in (employee.tasks)")
    List<Employee> findEmployeesWorkingOnTask(@Param("task") Task task);

    List<Employee> findEmployeesByCity(String city);

    List<Employee> findEmployeesByName(String name);

    Employee  findEmployeesByEmail(String email);

    List<Employee> findEmployeesBySurname(String surname);

    List<Employee> findEmployeesByPosition(Position position);

    List<Employee> findEmployeesBySalaryBetween(Double lowerLimit, Double upperLimit);
}
