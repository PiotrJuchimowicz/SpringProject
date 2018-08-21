package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends GenericRepository<Department> {

    List<Department> findByLocation(String location);

    List<Department> findByName(String name);
}
