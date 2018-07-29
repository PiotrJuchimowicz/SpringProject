package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
/*@NoArgsConstructor*/
/*@ToString*/
/*@EqualsAndHashCode*/
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", updatable = false, nullable = false)
    private Long id;


    private String name, surname, email;
    private double salary;

    protected  Person(){}
    protected Person(String name, String surname, String email, double salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.salary = salary;
    }
}
