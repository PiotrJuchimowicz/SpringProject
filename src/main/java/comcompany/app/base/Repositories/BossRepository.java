package comcompany.app.base.Repositories;

import comcompany.app.base.Models.Boss;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface BossRepository extends  GenericRepository<Boss> {


}
