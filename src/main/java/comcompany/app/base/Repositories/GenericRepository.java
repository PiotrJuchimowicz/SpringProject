package comcompany.app.base.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

//Spring musi wiedziec w czasie uruchamiania apki jakimi typami rozszerzam JpaRepositroy. Tu mu tego nie podalem
//bo jest T,wiec oznaczam klase adnotacja @NoRepositoryBean i spring nie robi mi z tego beana
//zrobi beany dla kazdego z repozytoriow stworzonych przeze mnie
@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T,Long> {
}
