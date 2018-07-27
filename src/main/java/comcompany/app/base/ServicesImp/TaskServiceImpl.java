package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Priority;
import comcompany.app.base.Models.Task;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task> implements TaskService {

    @Autowired
    public void setRepository(GenericRepository<Task> taskRepository) {
        this.setGenericRepository(taskRepository);
    }



    @Override
    public List<Task> findTasksInTimeRange(LocalDate lowerRange, LocalDate upperRange) {
        return null;
    }

    @Override
    public List<Task> findByPriority(Priority priority) {
        return null;
    }

    @Override
    public List<Task> findTasksRelatedWithEmployee(Employee employee) {
        return null;
    }
}
