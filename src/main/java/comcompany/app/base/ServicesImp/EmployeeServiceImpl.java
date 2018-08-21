package comcompany.app.base.ServicesImp;

import comcompany.app.base.Exceptions.IncorrectFormDataException;
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

//TODO
//optionals instead nulls throwing
@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee> implements EmployeeService {

    @Autowired
    public void setRepository(GenericRepository<Employee> employeeRepository) {
        this.setGenericRepository(employeeRepository);
    }

    @Override
    public List<Employee> findEmployeesByDepartment(Department department) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesByDepartment(department);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by department : " + department);
            throw new NullQueryResultException("Unable to find employees by department : " + department);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByPosition(Position position) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesByPosition(position);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by position : " + position);
            throw new NullQueryResultException("Unable to find employees by position : " + position);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesBySalaryBetween(Double lowerLimit, Double upperLimit) {
        if ((lowerLimit == null || upperLimit == null) || (lowerLimit < 0 || upperLimit < 0)) {
            throw new IncorrectFormDataException("Salary cant be less than 0 or null");
        }
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesBySalaryBetween(lowerLimit, upperLimit);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees  who earns between  : " + lowerLimit + " and " + upperLimit);
            throw new NullQueryResultException("Unable to find employees  who earns between  : " + lowerLimit + " and " + upperLimit);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesWorkingOnTask(Task task) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesWorkingOnTask(task);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees working on task : " + task);
            throw new NullQueryResultException("Unable to find employees working on task : " + task);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByCity(String city) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesByCity(city);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by city : " + city);
            throw new NullQueryResultException("Unable to find employees by city : " + city);
        }

        return querryResult;

    }

    @Override
    public List<Employee> findEmployeesByName(String name) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesByName(name);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by name : " + name);
            throw new NullQueryResultException("Unable to find employees by name   : " + name);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesBySurname(String surname) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesBySurname(surname);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by surname : " + surname);
            throw new NullQueryResultException("Unable to find employees by surname   : " + surname);
        }

        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByEmail(String email) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesByEmail(email);
        if (querryResult == null) {
            this.getLog().warn("Unable to find employees by email : " + email);
            throw new NullQueryResultException("Unable to find employees by email   : " + email);
        }

        return querryResult;

    }

}
