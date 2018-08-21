package comcompany.app.base.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*
Spring Data need to know which type of object will be persisted and type of its id(below I didnt give it to Spring)
In this case,I decided to increase abstraction-from this layer will be created 3 different layers(non generic)
So this interface must be generic
I've used @NonRepositoryBean annotation which stops Spring before creating below Repository
 */
@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long> {
}
