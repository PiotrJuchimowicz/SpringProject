package comcompany.app.base.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Boss")
@Table(name = "BOSS")
@ToString( includeFieldNames = true,callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Boss extends Person {
    private double companyBudget;

    public Boss(String name, String surname, String email, double salary, double companyBudget) {
        super(name, surname, email, salary);
        this.companyBudget = companyBudget;
    }
}
