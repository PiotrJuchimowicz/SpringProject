package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Department;
import comcompany.app.base.Repositories.DepartmentRepository;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department> implements DepartmentService {

    @Autowired
    public void setRepository(GenericRepository<Department> departmentRepository) {
        this.setGenericRepository(departmentRepository);
    }




    @Override
    public List<Department> findByLocation(String location) {
        DepartmentRepository departmentRepository = (DepartmentRepository)this.getGenericRepository();
        List<Department> querryResult = departmentRepository.findByLocation(location);
        if(querryResult==null)
        {
            this.getLog().error("Unable to find Departments by " + location);
            throw new RuntimeException("Unable to find Departments by");
        }


        return querryResult;



    }

    @Override
    public List<Department> findByName(String name) {
        return null;
    }
}
