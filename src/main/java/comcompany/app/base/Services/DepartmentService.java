package comcompany.app.base.Services;

import comcompany.app.base.Models.Department;

import java.util.List;


public interface DepartmentService extends GenericService<Department> {

    List<Department> findByLocation(String location);

    List<Department> findByName(String name);
}
