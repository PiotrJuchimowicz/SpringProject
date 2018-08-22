package comcompany.app.base.ServicesImp;

import comcompany.app.base.Exceptions.IncorrectFormDataException;
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
    public void setRepository(GenericRepository<Employee> employeeRepository) {
        this.setGenericRepository(employeeRepository);
    }

    @Override
    public List<Employee> findEmployeesByDepartment(Department department) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesByDepartment(department);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByPosition(Position position) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesByPosition(position);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesBySalaryBetween(Double lowerLimit, Double upperLimit) {
        if ((lowerLimit == null || upperLimit == null) || (lowerLimit < 0 || upperLimit < 0) || lowerLimit>upperLimit) {
            throw new IncorrectFormDataException("Salary cant be less than 0 or null");
        }
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesBySalaryBetween(lowerLimit, upperLimit);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesWorkingOnTask(Task task) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesWorkingOnTask(task);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByCity(String city) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();
        List<Employee> querryResult = employeeRepository.findEmployeesByCity(city);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByName(String name) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesByName(name);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesBySurname(String surname) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesBySurname(surname);
        return querryResult;
    }

    @Override
    public List<Employee> findEmployeesByEmail(String email) {
        EmployeeRepository employeeRepository = (EmployeeRepository) this.getGenericRepository();

        List<Employee> querryResult = employeeRepository.findEmployeesByEmail(email);
        return querryResult;

    }

}
