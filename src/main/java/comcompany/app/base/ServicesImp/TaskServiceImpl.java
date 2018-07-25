package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Task;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task> implements TaskService {

    @Autowired
    public void setRepository(GenericRepository<Task> taskRepository) {
        this.setGenericRepository(taskRepository);
    }

}
