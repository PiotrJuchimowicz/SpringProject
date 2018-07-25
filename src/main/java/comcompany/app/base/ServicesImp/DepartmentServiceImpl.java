package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department> implements DepartmentService {

    @Autowired
    public void setRepository(GenericRepository<Department> departmentRepository) {
        this.setGenericRepository(departmentRepository);
    }
}
