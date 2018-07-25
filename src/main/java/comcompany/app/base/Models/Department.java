package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

//Need to add later  a manager for each department(iheritance implementation in Hibernate)
@Entity(name = "Department")
@Table(name = "DEPARTMENT")
@ToString(exclude = "employees", includeFieldNames = true)
@EqualsAndHashCode(exclude = "employees")
@NoArgsConstructor
@Getter
@Setter
public class Department {

    //Hibernate generates id at object creation time no before pushing to DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", updatable = false, nullable = false)
    private Long id;
    private String name,location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees;


    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }


}
