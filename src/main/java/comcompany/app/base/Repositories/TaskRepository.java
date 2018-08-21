package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Employee;
import comcompany.app.base.Models.Priority;
import comcompany.app.base.Models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends GenericRepository<Task> {

    @Query(value = "SELECT t FROM  Task t  WHERE t.endDate BETWEEN :lowerRange AND :upperRange", nativeQuery = false)
    List<Task> findFinishedTasksInTimeRange(@Param("lowerRange") LocalDate lowerRange, @Param("upperRange") LocalDate upperRange);


    @Query("select task From Task task where :employee in (task.employees)")
    List<Task> findTasksRelatedWithEmployee(@Param("employee") Employee employee);

    List<Task> findByPriority(Priority priority);

    List<Task> findByName(String name);


}
