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
@ToString(exclude = {"department","tasks"}, includeFieldNames = true)
@EqualsAndHashCode(exclude = {"tasks","department"})
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", updatable = false, nullable = false)
    private Long id;

    private String name, surname;
    @Enumerated(EnumType.STRING)
    private Position position;
    private double salary;

    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Task> tasks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;


    public Employee(String name, String surname, Position position, double salary) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.salary = salary;

    }





}
