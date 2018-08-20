package comcompany.app.base.Models;

import lombok.*;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "Employee")
@Table(name = "EMPLOYEE")
@Getter
@Setter
@ToString(exclude = {"department","tasks","id"})
@EqualsAndHashCode(exclude = {"department","tasks"})
public  class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", updatable = false, nullable = false)
    private Long id;


    private String name, surname, email,city;
    private double salary;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Task> tasks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "recipient",cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    private List<Message> receivedMessages;



    public Employee() {
    }

    public Employee(String name, String surname, String email, double salary, Position position) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.salary = salary;
        this.position = position;
    }
}
