package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Task")
@Table(name = "DEPARTMENT")
//mozna kiedys zrobic ze departament ma kierownika(dziedziczenie a Hibernate)
@ToString(exclude = "employees")
@NoArgsConstructor
@Constructor
public class Department {

    //id generuja sie chyba(na 99 %) w chwili stworzenia obiektu a nie w chwili dodania go do bazy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id",updatable = false,nullable = false)

    @Getter@Setter
    private Long id ;

    @Getter@Setter
    private String name,location;


    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Department() {
    }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, location);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
