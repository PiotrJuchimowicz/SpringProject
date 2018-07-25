package comcompany.app.base.Services;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Priority;
import comcompany.app.base.Models.Task;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface TaskService extends GenericService<Task> {
    List<Task> findTasksInTimeRange(LocalDate lowerRange, LocalDate upperRange);
    List<Task> findByPriority(Priority priority);
    List<Task> findTasksRelatedWithEmployee( Employee employee);

}
