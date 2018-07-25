package comcompany.app.base.Models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

//Task is owner side
@Entity(name = "Task")
@Table(name = "TASK")
@ToString(exclude = "employees", includeFieldNames = true)
@EqualsAndHashCode(exclude = "employees")
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id",updatable = false,nullable = false)
    private Long id;

    private String name,description;
    private LocalDate startDate, endDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    /*JoinCollumns-collumn points to owner side
    inverseJoinCollumns-collumn points to inverse side
    Join Table consists of task_id and employee_id*/
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "TASK_EMPLOYEE",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees;


    public Task(String name, String description, LocalDate startDate, LocalDate endDate, Priority priority) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
    }


}
