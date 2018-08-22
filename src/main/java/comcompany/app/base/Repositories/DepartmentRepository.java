package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

//Methods return empty collections or collections of objects meeting the criteria
@Repository
public interface DepartmentRepository extends GenericRepository<Department> {

    List<Department> findByLocation(String location);

    List<Department> findByName(String name);
}
