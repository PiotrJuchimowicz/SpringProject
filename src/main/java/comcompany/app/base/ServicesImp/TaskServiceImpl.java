package comcompany.app.base.ServicesImp;

import comcompany.app.base.Exceptions.NullQueryResultException;
import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Priority;
import comcompany.app.base.Models.Task;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Repositories.TaskRepository;
import comcompany.app.base.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task> implements TaskService {

    @Autowired
    public void setRepository(GenericRepository<Task> taskRepository) {
        this.setGenericRepository(taskRepository);
    }



    @Override
    public List<Task> findFinishedTasksInTimeRange(LocalDate lowerRange, LocalDate upperRange) {

        TaskRepository taskRepository = (TaskRepository) this.getGenericRepository();
        List<Task> queryResult = taskRepository.findFinishedTasksInTimeRange(lowerRange, upperRange);

        if (queryResult == null)
        {
            this.getLog().warn("Unable to find tasks finished between " + lowerRange +" and " + upperRange);
            throw new NullQueryResultException("Unable to find tasks finished between " + lowerRange +" and " + upperRange);
        }

        return queryResult;
    }

    @Override
    public List<Task> findByPriority(Priority priority) {
        TaskRepository taskRepository = (TaskRepository) this.getGenericRepository();
        List<Task> queryResult = taskRepository.findByPriority(priority);

        if (queryResult == null)
        {
            this.getLog().warn("Unable to find tasks by priority : " + priority );
            throw new NullQueryResultException("Unable to find tasks by priority : " + priority );
        }

        return queryResult;
    }

    @Override
    public List<Task> findTasksRelatedWithEmployee(Employee employee) {
        TaskRepository taskRepository = (TaskRepository) this.getGenericRepository();
        List<Task> queryResult = taskRepository.findTasksRelatedWithEmployee(employee);

        if (queryResult == null)
        {
            this.getLog().warn("Unable to find tasks related with  : " + employee );
            throw new NullQueryResultException("Unable to find tasks related with  : " + employee  );
        }

        return queryResult;
    }

    @Override
    public List<Task> findByName(String name) {
        TaskRepository taskRepository = (TaskRepository) this.getGenericRepository();
      List<Task> queryResult=taskRepository.findByName(name);

        if (queryResult == null)
        {
            this.getLog().warn("Unable to find tasks by  : " + name );
            throw new NullQueryResultException("Unable to find tasks by  : " + name  );
        }

        return  queryResult;

    }
}
