package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/*Many Employees with Many Tasks (Task is relation owner)
Many Employees with One Department(Employee is relation owner - has fk)*/
@Entity(name = "Employee")
@Table(name = "EMPLOYEE")
@ToString(exclude = {"department", "tasks"}, includeFieldNames = true,callSuper = true)
@EqualsAndHashCode(exclude = {"tasks", "department"},callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Employee extends Person {

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Task> tasks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(String name, String surname, String email, double salary, Position position, Department department) {
        super(name, surname, email, salary);
        this.position = position;
        this.department = department;
    }




}
