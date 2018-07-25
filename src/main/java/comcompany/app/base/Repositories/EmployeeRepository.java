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
//jesli mam fetch na lazy :
//zwykle join pobierze mi samych employee bez powiazanych z nimi obiektow
//join fetch pobierze i employee i powiazane z nimi obiekty

    //to jest jpql w tym przypadku nie wyglada jak sql( normalnie powinien być join)
    @Query("select t.employees From Task t Where t.id =:task.id ")
    List<Employee> findEmployeesWorkingOnTask(Task task);

}
