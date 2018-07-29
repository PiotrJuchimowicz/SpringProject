package comcompany.app.base.ServicesImp;

import comcompany.app.base.Exceptions.NullQueryResultException;
import comcompany.app.base.Models.Department;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Position;
import comcompany.app.base.Models.Task;
import comcompany.app.base.Repositories.EmployeeRepository;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee> implements EmployeeService {

    @Autowired
    public void setRepository(GenericRepository<Employee> employeeRepository)
    {
        this.setGenericRepository(employeeRepository);
    }



    @Override
    public List<Employee> findEmployeesByDepartment(Department department) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult =  employeeRepository.findEmployeesByDepartment(department);
        if(querryResult==null)
        {
            this.getLog().warn("Unable to find employees by department : " + department);
            throw new NullQueryResultException("Unable to find employees by department : " + department);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByPosition(Position position) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult =  employeeRepository.findEmployeesByPosition(position);
        if(querryResult==null)
        {
            this.getLog().warn("Unable to find employees by position : " + position);
            throw new NullQueryResultException("Unable to find employees by position : " + position);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesBySalaryBetween(double lowerLimit, double upperLimit) {

        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult =  employeeRepository.findEmployeesBySalaryBetween(lowerLimit,upperLimit);
        if(querryResult==null)
        {
            this.getLog().warn("Unable to find employees  who earns between  : " + lowerLimit + " and " + upperLimit);
            throw new NullQueryResultException("Unable to find employees  who earns between  : " + lowerLimit +" and " + upperLimit);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesWorkingOnTask(Task task) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult =  employeeRepository.findEmployeesWorkingOnTask(task);
        if(querryResult==null)
        {
            this.getLog().warn("Unable to find employees working on task : " + task);
            throw new NullQueryResultException("Unable to find employees working on task : " + task);
        }

        return querryResult;
    }
}
