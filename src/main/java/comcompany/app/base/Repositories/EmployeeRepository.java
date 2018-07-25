package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends GenericRepository<Employee> {

    List<Employee> findEmployeesByDepartment(Department department);

    List<Employee> findEmployeesByPosition(Position position);

    List<Employee> findEmployeesBySalaryBetween(double lowerLimit, double upperLimit);


    /*//jpql querry(with sql  there should be join  expression )
    @Query("select t.employees From Task t Where t.id =:task.id ")
    List<Employee> findEmployeesWorkingOnTask(Task task);*/

}
