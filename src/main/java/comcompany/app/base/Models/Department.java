package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity(name = "Department")
@Table(name = "DEPARTMENT")
@ToString(exclude = {"employees", "id"}, includeFieldNames = true)
@EqualsAndHashCode(exclude = "employees")
@NoArgsConstructor
@Getter
@Setter
//TODO divide into Entity and Dto
public class Department {
    //Hibernate generates id at object creation time not before pushing to DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", updatable = false, nullable = false)
    private Long id;
    private String name, location;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
