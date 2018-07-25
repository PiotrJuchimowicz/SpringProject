package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends GenericRepository<Employee> {

    List<Employee> findEmployeesByDepartment(Department department);

    List<Employee> findEmployeesByPosition(Position position);

    List<Employee> findEmployeesBySalaryBetween(double lowerLimit, double upperLimit);


    //jpql querry(with sql  there should be join  expression )
    @Query("select employee From Employee employee join employee.tasks task where employee=:task")
    List<Employee> findEmployeesWorkingOnTask(@Param("task") Task task);

}
