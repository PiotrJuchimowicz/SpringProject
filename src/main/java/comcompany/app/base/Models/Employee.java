package comcompany.app.base.Models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Employee")
@Table(name = "EMPLOYEE")
@Getter
@Setter
@ToString(exclude = {"department", "tasks", "id"})
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", updatable = false, nullable = false)
    private Long id;
    private String name, surname, email, city;
    private double salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Task> tasks;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    public Employee() {
    }

    public Employee(String name, String surname, String email, String city, double salary, Position position) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.salary = salary;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
